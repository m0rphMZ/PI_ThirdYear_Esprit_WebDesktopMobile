<?php

namespace App\Controller;

use App\Entity\User;
use App\Entity\Reponses;
use App\Entity\Reclamation;
use Symfony\Component\Mime\Email;
use App\Repository\ReponsesRepository;
use App\Repository\ReclamationRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;



class ReclamationController extends AbstractController
{
    #[Route('/reclamation', name: 'app_reclamation')]
    public function index(Request $request, PaginatorInterface $paginator): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
    
        // Create the status filter form
        $form = $this->createFormBuilder()
            ->add('status', ChoiceType::class, [
                'choices' => [
                    'Tous les Reclamations' => '',
                    'Reclamation(s) Ouverte' => 'Ouvert',
                    'Reclamation(s) Fermée' => 'Fermée',
                ],
                'required' => false,
                'placeholder' => false,
            ])
            ->getForm();
    
        $form->handleRequest($request);
    
        $statusFilter = null;
    
        if ($form->isSubmitted() && $form->get('status')->getData() !== '') {
            $statusFilter = $form->get('status')->getData();
        }
    
        // Get the reclamations based on the selected status filter
        if ($statusFilter === null) {
            $user_connected=new User();
            $session = $request->getSession();
            $user_connected=$session->get('user');
            $reclamations = $entityManager->getRepository(Reclamation::class)->findByUserId($user_connected->getIdUser());
        } else {
            $user_connected=new User();
            $session = $request->getSession();
            $user_connected=$session->get('user');
            $reclamations = $entityManager->getRepository(Reclamation::class)->findReclamationsByStatus($statusFilter, $user_connected->getIdUser());
        }
    
        $pagination = $paginator->paginate(
            $reclamations,
            $request->query->getInt('page', 1),
            3
        );
    
        return $this->render('reclamation/rec.html.twig', [
            'reclamations' => $pagination,
            'statusFilterForm' => $form->createView(),
        ]);
    }





    #[Route('/reclamation/search', name: 'app_reclamation_search')]
public function search(Request $request, ReclamationRepository $reclamationRepository, PaginatorInterface $paginator): Response
{
    $term = $request->query->get('q');
    $session = $request->getSession();
    $userId = $session->get('user')->getIdUser();

    // Get the status filter value from the query parameters
    $statusFilter = $request->query->get('statusFilter');

    $reclamations = $reclamationRepository->searchReclamations($term, $userId, $statusFilter);

    $pagination = $paginator->paginate(
        $reclamations,
        $request->query->getInt('page', 1),
        3
    );

    // Create the status filter form
    $statusFilterForm = $this->createFormBuilder()
        ->add('status', ChoiceType::class, [
            'choices' => [
                'Tous les Reclamations' => '',
                'Reclamation(s) Ouverte' => 'Ouvert',
                'Reclamation(s) Fermée' => 'Fermée',
            ],
            'required' => false,
            'placeholder' => false,
            // Set the default value to the value from the query parameter
            'data' => $statusFilter,
        ])
        ->getForm();

    return $this->render('reclamation/rec.html.twig', [
        'reclamations' => $pagination,
        'statusFilterForm' => $statusFilterForm->createView(),
    ]);
}








//     #[Route('/reclamation/search', name: 'app_reclamation_search')]
// public function search(Request $request, ReclamationRepository $reclamationRepository, PaginatorInterface $paginator): Response
// {
//     $term = $request->query->get('q');
//     $session = $request->getSession();
//     $userId = $session->get('user')->getIdUser();

//     $reclamations = $reclamationRepository->searchReclamations($term, $userId);

//     $pagination = $paginator->paginate(
//         $reclamations,
//         $request->query->getInt('page', 1),
//         3
//     );

//     // Create the status filter form
//     $statusFilterForm = $this->createFormBuilder()
//         ->add('status', ChoiceType::class, [
//             'choices' => [
//                 'Tous les Reclamations' => '',
//                 'Reclamation(s) Ouverte' => 'Ouvert',
//                 'Reclamation(s) Fermée' => 'Fermée',
//             ],
//             'required' => false,
//             'placeholder' => false,
//         ])
//         ->getForm();

//     return $this->render('reclamation/rec.html.twig', [
//         'reclamations' => $pagination,
//         'statusFilterForm' => $statusFilterForm->createView(),
//     ]);
// }



    #[Route('/reclamation/new', name: 'new_reclamation')]
    public function new(Request $request): Response
    {
        $reclamation = new Reclamation();
        $form = $this->createFormBuilder($reclamation)
            ->add('titre_rec', TextType::class, [
                'label' => 'Titre de réclamation :',
                'constraints' => [
                    new NotBlank([
                        'message' => 'Le titre de la réclamation ne doit pas être vide',
                    ]),
                    new Length([
                        'min' => 5,
                        'minMessage' => 'Le titre de la réclamation doit avoir au moins {{ limit }} caractères',
                    ]),
                ],
            ])
            ->add('type', ChoiceType::class, [
                'label' => 'Type de reclamation :',
                'placeholder' => 'Veuillez sélectionner un type de réclamation',
                'choices' => [
                    'Un problème avec votre expérience utilisateur' => 'User',
                    'Un problème avec votre/vos billet(s)' => 'Ticket',
                    'Un problème avec un événement' => 'Evénement',
                    'Un autre type daide' => 'Autre aide',
                ],
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez sélectionner un type de réclamation',
                    ]),
                ],
                'invalid_message' => 'Veuillez sélectionner un type de réclamation',
                'attr' => [
                    'onchange' => 'removePlaceholderOption()',
                ],
            ])
            ->add('description', TextareaType::class, [
                'label' => 'Description :',
            ])
            ->getForm();

        $form->handleRequest($request);
        

        if ($form->isSubmitted() && $form->isValid()) {
            $reclamation = $form->getData();
            $reclamation->setDateCreation(new \DateTime());
            $entityManager = $this->getDoctrine()->getManager();
            $user_connected=new User();
                $session = $request->getSession();
                $user_connected=$session->get('user');
            $user = $entityManager->getRepository(User::class)->find($user_connected->getIdUser());
            $reclamation->setUser($user);
            $entityManager->persist($reclamation);
            $entityManager->flush();

            return $this->redirectToRoute('app_reclamation');
        }

        return $this->render('reclamation/newRec.html.twig', [
            'form' => $form->createView(),
        ]);
    }


    #[Route('/reclamation/{id}', name: 'show_reclamationById')]
    public function show(Request $request, int $id, ReponsesRepository $reponsesRepository, \Symfony\Component\Mailer\MailerInterface $mailer): Response
{
    $entityManager = $this->getDoctrine()->getManager();
    $reclamation = $entityManager->getRepository(Reclamation::class)->find($id);

    if (!$reclamation) {
        throw $this->createNotFoundException('Reclamation not found');
    }

    $reponses = $reponsesRepository->findResponsesByReclamation($reclamation);

    $reponse = new Reponses();
    $form = $this->createFormBuilder($reponse)
        ->add('rep_description', TextareaType::class, [
            'label' => 'Description :',
            'constraints' => [
                new NotBlank([
                    'message' => 'Veuillez ajouter une description pour votre réponse',
                ]),
                new Length([
                    'min' => 2,
                    'minMessage' => 'Votre réponse doit avoir au moins {{ limit }} caractères',
                ]),
            ],
        ])
        ->getForm();

    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $reponse = $form->getData();
        $reponse->setDateRep(new \DateTime());
        $entityManager = $this->getDoctrine()->getManager();
        $user_connected = new User();
        $session = $request->getSession();
        $user_connected = $session->get('user');
        $user = $entityManager->getRepository(User::class)->find($user_connected->getIdUser());
        $reponse->setUser($user);
        $reponse->setRec($reclamation);
        $entityManager->persist($reponse);
        $entityManager->flush();

        // Send email to user
        $email = (new Email())
            ->from('touskieart.reclamations@gmail.com')
            ->to($reclamation->getUser()->getEmail())
            ->subject('Touskieart - Nouvelle réponse ajoutée à votre réclamation')
            ->html('<p>Bonjour, ' . $reclamation->getUser()->getNom() . ',</p><p> Votre réponse a été ajoutée à la réclamation "' . $reclamation->getTitreRec() . '".</p><p>Cliquez sur le bouton ci-dessous pour voir votre réclamation :</p><p><a href="' . $this->generateUrl('show_reclamationById', ['id' => $reclamation->getRecId()], UrlGeneratorInterface::ABSOLUTE_URL) . '">Voir la réclamation</a></p><p>Merci,</p><p>Touskieart team</p>');
        $mailer->send($email);

        return $this->redirectToRoute('show_reclamationById', ['id' => $reclamation->getRecId()]);
    }

    return $this->render('reclamation/showRec.html.twig', [
        'reclamation' => $reclamation,
        'reponses' => $reponses,
        'form' => $form->createView(),
    ]);
}
    



    #[Route('/reclamation/{id}/delete', name: 'delete_reclamation')]

        public function deleteReclamation(Request $request, int $id): Response
        {
            $entityManager = $this->getDoctrine()->getManager();
            $reclamation = $entityManager->getRepository(Reclamation::class)->find($id);

            if (!$reclamation) {
                throw $this->createNotFoundException(
                    'No reclamation found for id '.$id
                );
            }

            $entityManager->remove($reclamation);
            $entityManager->flush();

            return $this->redirectToRoute('app_reclamation');
        }




        #[Route('/admin/reclamations', name: 'app_reclamations_admin')]
        public function indexAdminRec(Request $request, PaginatorInterface $paginator): Response
        {
            $entityManager = $this->getDoctrine()->getManager();
            $queryBuilder = $entityManager->getRepository(Reclamation::class)->findAllReclamations();
        
            $pagination = $paginator->paginate(
                $queryBuilder,
                $request->query->getInt('page', 1),
                3 // number of items per page
            );
        
            return $this->render('reclamation/adminAllRec.html.twig', [
                'reclamations' => $pagination,
            ]);
        }



        #[Route('/admin/reclamations/search', name: 'app_reclamations_admin_search')]
            public function indexAdminRecSearch(Request $request, PaginatorInterface $paginator, ReclamationRepository $reclamationRepository): Response
            {
                $term = $request->query->get('q');

                $queryBuilder = $reclamationRepository->findAllReclamationsBySearchTerm($term);

                $pagination = $paginator->paginate(
                    $queryBuilder,
                    $request->query->getInt('page', 1),
                    3 // number of items per page
                );

                return $this->render('reclamation/adminAllRec.html.twig', [
                    'reclamations' => $pagination,
                ]);
            }

        





    

    #[Route('admin/reclamations/{id}', name: 'show_reclamationById_admin')]
    public function showRecAdmin(Request $request, int $id, ReponsesRepository $reponsesRepository, \Symfony\Component\Mailer\MailerInterface $mailer): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
        $reclamation = $entityManager->getRepository(Reclamation::class)->find($id);
    
        if (!$reclamation) {
            throw $this->createNotFoundException('Reclamation not found');
        }
    
        $reponses = $reponsesRepository->findResponsesByReclamation($reclamation);
    
        $reponse = new Reponses();
        $form = $this->createFormBuilder($reponse)
            ->add('rep_description', TextareaType::class, [
                'label' => 'Description :',
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez ajouter une description pour votre réponse',
                    ]),
                    new Length([
                        'min' => 2,
                        'minMessage' => 'Votre réponse d\'administrateur doit avoir au moins {{ limit }} caractères',
                    ]),
                ],
            ])
            ->getForm();
    
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $reponse = $form->getData();
            $reponse->setDateRep(new \DateTime());
            $entityManager = $this->getDoctrine()->getManager();
            $user_connected=new User();
            $session = $request->getSession();
            $user_connected=$session->get('user');
        $user = $entityManager->getRepository(User::class)->find($user_connected->getIdUser());
            $reponse->setUser($user);
            $reponse->setRec($reclamation);
            $reponse->setIsAdminReponse(true);
            $entityManager->persist($reponse);
            $entityManager->flush();

            $email = (new Email())
            ->from('touskieart.reclamations@gmail.com')
            ->to($reclamation->getUser()->getEmail())
            ->subject('Touskieart - Réponse Administrateur ajoutée à votre réclamation')
            ->html('<p>Bonjour, ' . $reclamation->getUser()->getNom() . ',</p><p> Un administrateur a répondu à votre réclamation "' . $reclamation->getTitreRec() . '".</p><p>Cliquez sur le bouton ci-dessous pour voir votre réclamation :</p><p><a href="' . $this->generateUrl('show_reclamationById', ['id' => $reclamation->getRecId()], UrlGeneratorInterface::ABSOLUTE_URL) . '">Voir la réclamation</a></p><p>Merci,</p><p>Touskieart team</p>');
        $mailer->send($email);
    
            return $this->redirectToRoute('show_reclamationById_admin', ['id' => $reclamation->getRecId()]);
        }
    
        return $this->render('reclamation/adminShowRec.html.twig', [
            'reclamation' => $reclamation,
            'reponses' => $reponses,
            'form' => $form->createView(),
        ]);
    }



    #[Route('admin/reclamations/{id}/close', name: 'close_reclamation')]

    public function closeReclamation(Reclamation $reclamation): Response
    {
        $reclamation->setStatus('Fermée');
        $reclamation->setDateFin(new \DateTime());
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();
        
        $this->addFlash('success', 'Reclamation '.$reclamation->getTitreRec().' a été fermée.');

        return $this->redirectToRoute('show_reclamationById_admin', ['id' => $reclamation->getRecId()]);
    }

    // // _________________________________________________________________________________________________________________

    //                                         JSON

    //  // _________________________________________________________________________________________________________________ 


    


}