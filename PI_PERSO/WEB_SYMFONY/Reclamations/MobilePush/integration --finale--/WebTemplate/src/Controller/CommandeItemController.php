<?php

namespace App\Controller;

use App\Entity\CommandeItem;
use App\Form\CommandeItemType;
use App\Repository\CommandeItemRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/commande_item")
 */
class CommandeItemController extends AbstractController
{
    /**
     * @Route("/", name="app_commande_item_index", methods={"GET"})
     */
    public function index(CommandeItemRepository $commandeItemRepository): Response
    {
        return $this->render('commande_item/index.html.twig', [
            'commande_items' => $commandeItemRepository->findAll(),
        ]);
    }

    /**
     * @Route("/showByCommande/{idC}", name="app_commande_item_index_commande", methods={"GET"})
     */
    public function indexbycommande(CommandeItemRepository $commandeItemRepository, int $idC): Response
    {
        return $this->render('commande_item/index.html.twig', [
            'commande_items' => $commandeItemRepository->findByCommande($idC),
        ]);
    }

    /**
     * @Route("/showByCommande/front/{idC}", name="app_commande_item_index_commande_front", methods={"GET"})
     */
    public function indexbycommandefront(CommandeItemRepository $commandeItemRepository, int $idC): Response
    {
        return $this->render('commande_item/mesItems.twig.html', [
            'items' => $commandeItemRepository->findByCommande($idC),
        ]);
    }

    /**
     * @Route("/new/{idC}", name="app_commande_item_new", methods={"GET", "POST"})
     */
    public function new($idC, Request $request, CommandeItemRepository $commandeItemRepository, \App\Repository\CommandeRepository $commandeRepository): Response
    {
        $commandeItem = new CommandeItem();
        $form = $this->createForm(CommandeItemType::class, $commandeItem);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $commandeItem->setCommande($commandeRepository->findOneBy(['idC' => $idC]));
            $commandeItemRepository->add($commandeItem, true);

            return $this->redirectToRoute('app_commande_item_index_commande', ['idC' => $idC], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('commande_item/new.html.twig', [
            'commande_item' => $commandeItem,
            'form' => $form,
        ]);
    }


    /**
     * @Route("/{id}", name="app_commande_item_show", methods={"GET"})
     */
    public function show(CommandeItem $commandeItem): Response
    {
        return $this->render('commande_item/show.html.twig', [
            'commande_item' => $commandeItem,
        ]);
    }

    /**
     * @Route("/{id}/edit/{idC}", name="app_commande_item_edit", methods={"GET", "POST"})
     */
    public function edit($idC, Request $request, CommandeItem $commandeItem, CommandeItemRepository $commandeItemRepository): Response
    {
        $form = $this->createForm(CommandeItemType::class, $commandeItem);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $commandeItemRepository->add($commandeItem, true);

            return $this->redirectToRoute('app_commande_item_index', ["idC" => $idC], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('commande_item/edit.html.twig', [
            'commande_item' => $commandeItem,
            'form' => $form,
        ]);
    }

    /**
     * @Route("/{id}/{idC}", name="app_commande_item_delete", methods={"POST"})
     */
    public function delete($idC, Request $request, CommandeItem $commandeItem, CommandeItemRepository $commandeItemRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $commandeItem->getId(), $request->request->get('_token'))) {
            $commandeItemRepository->remove($commandeItem, true);
        }

        return $this->redirectToRoute('app_commande_item_index_commande', ['idC' => $idC], Response::HTTP_SEE_OTHER);
    }
}
