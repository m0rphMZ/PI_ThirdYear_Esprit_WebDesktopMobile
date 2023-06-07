<?php

namespace App\Controller;

use App\Entity\Commande;
use App\Form\CommandeType;
use App\Repository\CommandeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


/**
 * @Route("/mobile/commandeItem")
 */
class CommandeItemControllerMobile extends AbstractController
{
    /**
     * @Route("/showByCommande/{idC}", name="app_commande_item_index_commande_front_mobile", methods={"GET"})
     */
    public function indexbycommandefront(\App\Repository\CommandeItemRepository $commandeItemRepository, int $idC, NormalizerInterface $normalizable): Response
    {

        $jsonContent = $normalizable->normalize($commandeItemRepository->findByCommande($idC), 'json', ['groups' => 'post:read']);

        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);
    }

    /**
     * @Route("/{id}", name="app_commande_item_delete_mobile", methods={"POST"})
     */
    public function delete(\App\Entity\CommandeItem $commandeItem, \App\Repository\CommandeItemRepository $commandeItemRepository, NormalizerInterface $normalizable): Response
    {
        $commandeItemRepository->remove($commandeItem, true);
        return new Response("commande items Suprimée avec succée", Response::HTTP_OK);
    }
}
