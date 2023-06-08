<?php

namespace App\Controller;

use App\Entity\User;
use App\Entity\Event;
use App\Entity\Commentaire;
use App\Form\CommentaireType;
use App\Repository\UserRepository;
use App\Repository\CommentaireRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

#[Route('/commentaire')]
class CommentaireController extends AbstractController
{
    #[Route('/{eventId}', name: 'app_commentaire_index', methods: ['GET'])]
    public function index(CommentaireRepository $commentaireRepository, UserRepository $userRepository, Event $event): Response
    {
        $eventId = $event->getEventId();
        $commentaires = $commentaireRepository->findByEventId($eventId);
        $commentairesAvecUsers = [];


        foreach ($commentaires as $commentaire) {
            $user = $userRepository->find($commentaire->getIdUser());
            $commentairesAvecUsers[] = ['commentaire' => $commentaire, 'user' => $user];
        }

        return $this->render('commentaire/index.html.twig', [
            'commentaires' => $commentairesAvecUsers,
            'eventId' => $eventId,
        ]);
    }





    #[Route('/new/{eventId}', name: 'app_commentaire_new', methods: ['GET', 'POST'])]
    public function new(Request $request, Event $event, CommentaireRepository $commentaireRepository): Response
    {
        $eventId = $event->getEventId();
        $commentaire = new Commentaire();
        $entityManager = $this->getDoctrine()->getManager();
        $user_connected=new User();
        $session = $request->getSession();
        $user_connected=$session->get('user');
        $session->set('user', $user_connected);
        $us=$entityManager->getRepository(User::class)->find($user_connected->getIdUser());
        
        $event=$entityManager->getRepository(Event::class)->find($eventId);
        
        $commentaire->setIdUser($us);
       $commentaire->setIdEvent($event);

        $form = $this->createForm(CommentaireType::class, $commentaire);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            


            $commentaireRepository->save($commentaire, true);

            return $this->redirectToRoute('app_event_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('commentaire/new.html.twig', [
            'commentaire' => $commentaire,
            'form' => $form,
            'eventId' => $eventId,
        ]);
    }

    #[Route('/{idCom}/edit', name: 'app_commentaire_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Commentaire $commentaire, CommentaireRepository $commentaireRepository): Response
    {
        $form = $this->createForm(CommentaireType::class, $commentaire);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $commentaireRepository->save($commentaire, true);

            return $this->redirectToRoute('app_event_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('commentaire/edit.html.twig', [
            'commentaire' => $commentaire,
            'form' => $form,
        ]);
    }

    #[Route('/{idCom}', name: 'app_commentaire_delete', methods: ['POST'])]
    public function delete(Request $request, Commentaire $commentaire, CommentaireRepository $commentaireRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$commentaire->getIdCom(), $request->request->get('_token'))) {
            $commentaireRepository->remove($commentaire, true);
        }

        return $this->redirectToRoute('app_event_index', [], Response::HTTP_SEE_OTHER);
    }
}