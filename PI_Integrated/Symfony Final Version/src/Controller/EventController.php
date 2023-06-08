<?php

namespace App\Controller;

use App\Entity\Event;
use App\Entity\User;
use App\Entity\Location;
use App\Form\EventType;
use App\Repository\EventRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

use Symfony\Component\HttpFoundation\JsonResponse;
use Knp\Component\Pager\PaginatorInterface;

//Commentaire Integration step1:
use App\Controller\CommentaireController;


#[Route('/event')]
class EventController extends AbstractController
{
    #[Route('/', name: 'app_event_index', methods: ['GET'])]
    public function index(Request $request, PaginatorInterface $paginator, EventRepository $eventRepository): Response
    {
        //Advanced Search Criterias:
        $criteria = [
            'title' => $request->query->get('title'),
            'type' => $request->query->get('type'),
            'startDate' => $request->query->get('startDate'),
            'endDate' => $request->query->get('endDate'),
            'minPrice' => $request->query->get('minPrice'),
            'maxPrice' => $request->query->get('maxPrice'),
        ];

        //Advanced Sort Criterias:
        $sortOptions = [
            'title_asc' => 'e.title',
            'type_asc' => 'e.type',
            'startdate' => 'e.startdate',
            'enddate' => 'e.enddate',
            'ticketprice' => 'e.ticketprice',
        ];
        $sort = $request->query->get('sort');
        if (isset($sortOptions[$sort])) {
            $sortCriteria = $sortOptions[$sort];
        } else {
            $sortCriteria = 'e.title'; // default sorting
        }

        $orderOptions = [
            'asc' => 'asc',
            'desc' => 'desc',
        ];
        $order = $request->query->get('order') ?? 'asc';

        if ($criteria) {
            $events = $eventRepository->search($criteria, $sortCriteria, $order);
        } else {
            $events = $eventRepository->findAll();
        }

        $pagination = $paginator->paginate(
            $events,
            $request->query->getInt('page', 1),
            4
        );

        return $this->render('event/index.html.twig', [
            'events' => $pagination,
            'criteria' => $criteria,
            'sort' => $sort,
        ]);
    }

    #[Route('/new', name: 'app_event_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EventRepository $eventRepository, SluggerInterface $slugger): Response
    {
        $user_connected=new User();
            $session = $request->getSession();
            $user_connected=$session->get('user');


        $event = new Event();
        $form = $this->createForm(EventType::class, $event, [
            'location' => $this->getDoctrine()->getRepository(Location::class)->findAll(),
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            //Get image path
            $image = $form->get('affiche')->getData();
            if ($image) {
                $originalFilename = pathinfo($image->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$image->guessExtension();

                // Move the file to the directory where user uploaded images are stored
                try {
                    $image->move(
                        $this->getParameter('user_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // sets the image name as affiche
                $event->setAffiche($newFilename);
            }

            // Get user Id & Location Id
            $userId = $user_connected->getIdUser();
            $entityManager = $this->getDoctrine()->getManager();
            $user = $entityManager->getRepository(User::class)->find($userId);
            //$location = $entityManager->getRepository(Location::class)->find(1);
            $event->setHost($user);
            //$event->setLocation($location);

            // Save Event
            $eventRepository->save($event, true);

            return $this->redirectToRoute('app_event_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('event/new.html.twig', [
            'event' => $event,
            'form' => $form,
        ]);
    }

    #[Route('/{eventId}', name: 'app_event_show', methods: ['GET'])]
    public function show(Event $event, CommentaireController $commentaireController): Response
    {
        $commentaireResponse = $commentaireController->forward('App\Controller\CommentaireController::index', [
            'eventId' => $event->getEventId(),
        ]);

        return $this->render('event/show.html.twig', [
            'event' => $event,
            'commentaireResponse' => $commentaireResponse->getContent(),
        ]);
    }
    #[Route('/{eventId}/edit', name: 'app_event_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Event $event, EventRepository $eventRepository, SluggerInterface $slugger): Response
    {
        $form = $this->createForm(EventType::class, $event);
        //Keep Previous Image:
        $form->remove('affiche');
        $form->add('affiche', FileType::class, [
            'label' => 'Affiche',
            'mapped' => false,
            'required' => false,
            'constraints' => [
                new File([
                    'maxSize' => '2Mi',
                    'mimeTypes' => [
                        'image/jpeg',
                        'image/png',
                        'image/gif'
                    ],
                    'mimeTypesMessage' => 'Veuillez choisir une image',
                ]),
            ],
        ]);

        $form->handleRequest($request);
        

        if ($form->isSubmitted() && $form->isValid()) {
            //Get image path
            $image = $form->get('affiche')->getData();
            if ($image) {
                $originalFilename = pathinfo($image->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$image->guessExtension();

                // Move the file to the directory where user uploaded images are stored
                try {
                    $image->move(
                        $this->getParameter('user_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // sets the image name as affiche
                $event->setAffiche($newFilename);
            }

            $eventRepository->save($event, true);

            return $this->redirectToRoute('app_event_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('event/edit.html.twig', [
            'event' => $event,
            'form' => $form,
        ]);
    }

    #[Route('/{eventId}', name: 'app_event_delete', methods: ['POST'])]
    public function delete(Request $request, Event $event, EventRepository $eventRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$event->getEventId(), $request->request->get('_token'))) {
            $eventRepository->remove($event, true);
        }

        return $this->redirectToRoute('app_event_index', [], Response::HTTP_SEE_OTHER);
    }



    //===============================
    //===========    Admin CRUD:
    //===============================
    #[Route('/admin/index', name: 'app_event_index_admin', methods: ['GET'])]
    public function adminIndex(Request $request, PaginatorInterface $paginator, EventRepository $eventRepository): Response
    {

        //Advanced Search Criterias:
        $criteria = [
            'title' => $request->query->get('title'),
            'type' => $request->query->get('type'),
            'startDate' => $request->query->get('startDate'),
            'endDate' => $request->query->get('endDate'),
            'minPrice' => $request->query->get('minPrice'),
            'maxPrice' => $request->query->get('maxPrice'),
        ];

        //Advanced Sort Criterias:
        $sortOptions = [
            'title_asc' => 'e.title',
            'type_asc' => 'e.type',
            'startdate' => 'e.startdate',
            'enddate' => 'e.enddate',
            'ticketprice' => 'e.ticketprice',
        ];
        $sort = $request->query->get('sort');
        if (isset($sortOptions[$sort])) {
            $sortCriteria = $sortOptions[$sort];
        } else {
            $sortCriteria = 'e.title'; // default sorting
        }

        $orderOptions = [
            'asc' => 'asc',
            'desc' => 'desc',
        ];
        $order = $request->query->get('order') ?? 'asc';

        if ($criteria) {
            $events = $eventRepository->search($criteria, $sortCriteria, $order);
        } else {
            $events = $eventRepository->findAll();
        }

        $pagination = $paginator->paginate(
            $events,
            $request->query->getInt('page', 1),
            4
        );
        return $this->render('event/admin/index.html.twig', [
            'events' => $pagination,
            'criteria' => $criteria,
            'sort' => $sort,
        ]);
    }

    #[Route('/admin/new', name: 'app_event_new_admin', methods: ['GET', 'POST'])]
    public function adminNew(Request $request, EventRepository $eventRepository, SluggerInterface $slugger): Response
    {
        $event = new Event();
        $form = $this->createForm(EventType::class, $event, [
            'location' => $this->getDoctrine()->getRepository(Location::class)->findAll(),
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            //Get image path
            $image = $form->get('affiche')->getData();
            if ($image) {
                $originalFilename = pathinfo($image->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$image->guessExtension();

                // Move the file to the directory where user uploaded images are stored
                try {
                    $image->move(
                        $this->getParameter('user_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // sets the image name as affiche
                $event->setAffiche($newFilename);
            }

            // Get user Id & Location Id
            $user_connected=new User();
            $session = $request->getSession();
            $user_connected=$session->get('user');

            $userId = $user_connected->getIdUser();
            $entityManager = $this->getDoctrine()->getManager();
            $user = $entityManager->getRepository(User::class)->find($userId);

            //$location = $entityManager->getRepository(Location::class)->find(1);
            $event->setHost($user);
            //$event->setLocation($location);

            // Save Event
            $eventRepository->save($event, true);

            return $this->redirectToRoute('app_event_index_admin', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('event/admin/new.html.twig', [
            'event' => $event,
            'form' => $form,
        ]);
    }

    #[Route('/admin/{eventId}', name: 'app_event_show_admin', methods: ['GET'])]
    public function adminShow(Event $event): Response
    {
        return $this->render('event/admin/show.html.twig', [
            'event' => $event,
        ]);
    }
	
	//Admin edit
    #[Route('/admin/{eventId}/edit', name: 'app_event_edit_admin', methods: ['GET', 'POST'])]
    public function adminEdit(Request $request, Event $event, EventRepository $eventRepository, SluggerInterface $slugger): Response
    {
        $form = $this->createForm(EventType::class, $event);
        //Keep Previous Image:
        $form->remove('affiche');
        $form->add('affiche', FileType::class, [
            'label' => 'Affiche',
            'mapped' => false,
            'required' => false,
            'constraints' => [
                new File([
                    'maxSize' => '2Mi',
                    'mimeTypes' => [
                        'image/jpeg',
                        'image/png',
                        'image/gif'
                    ],
                    'mimeTypesMessage' => 'Veuillez choisir une image',
                ]),
            ],
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            //Get image path
            $image = $form->get('affiche')->getData();
            if ($image) {
                $originalFilename = pathinfo($image->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$image->guessExtension();

                // Move the file to the directory where user uploaded images are stored
                try {
                    $image->move(
                        $this->getParameter('user_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // sets the image name as affiche
                $event->setAffiche($newFilename);
            }

            $eventRepository->save($event, true);

            return $this->redirectToRoute('app_event_index_admin', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('event/admin/edit.html.twig', [
            'event' => $event,
            'form' => $form,
        ]);
    }
	
	//Admin Delete
    #[Route('/admin/{eventId}', name: 'app_event_delete_admin', methods: ['POST'])]
    public function adminDelete(Request $request, Event $event, EventRepository $eventRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$event->getEventId(), $request->request->get('_token'))) {
            $eventRepository->remove($event, true);
        }

        return $this->redirectToRoute('app_event_index_admin', [], Response::HTTP_SEE_OTHER);
    }

    
    #[Route('/admin/index', name: 'searchEventx')]
    public function searchEventx(Request $request, NormalizerInterface $Normalizer, EventRepository $er)
    {
        $repository=$this->getDoctrine()->getRepository (Event::class);
        $requestString=$request->get('searchValue');
        $events = $er->findEventByTitle($requestString);
        $jsonContent = $Normalizer->normalize($events, 'json', ['groups' => 'events']);
        $retour=json_encode($jsonContent);
        return new Response($retour);
    }
}