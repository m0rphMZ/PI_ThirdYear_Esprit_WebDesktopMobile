<?php

namespace App\Controller;

use App\Entity\Redection;
use App\Form\RedectionType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/redection')]
class RedectionController extends AbstractController
{
    #[Route('/', name: 'app_redection_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $redections = $entityManager
            ->getRepository(Redection::class)
            ->findAll();

        return $this->render('redection/index.html.twig', [
            'redections' => $redections,
        ]);
    }

    #[Route('/new', name: 'app_redection_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $redection = new Redection();
        $form = $this->createForm(RedectionType::class, $redection);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($redection);
            $entityManager->flush();

            return $this->redirectToRoute('app_redection_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('redection/new.html.twig', [
            'redection' => $redection,
            'form' => $form,
        ]);
    }

    #[Route('/{coder}', name: 'app_redection_show', methods: ['GET'])]
    public function show(Redection $redection): Response
    {
        return $this->render('redection/show.html.twig', [
            'redection' => $redection,
        ]);
    }

    #[Route('/{coder}/edit', name: 'app_redection_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Redection $redection, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(RedectionType::class, $redection);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_redection_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('redection/edit.html.twig', [
            'redection' => $redection,
            'form' => $form,
        ]);
    }

    #[Route('/{coder}', name: 'app_redection_delete', methods: ['POST'])]
    public function delete(Request $request, Redection $redection, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$redection->getCoder(), $request->request->get('_token'))) {
            $entityManager->remove($redection);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_redection_index', [], Response::HTTP_SEE_OTHER);
    }
}
