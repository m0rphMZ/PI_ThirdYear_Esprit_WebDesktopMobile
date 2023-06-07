<?php

namespace App\Controller;

use App\Entity\Commande;
use App\Form\CommandeType;
use App\Repository\CommandeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


/**
 * @Route("/mobile/livraison")
 */
class LivraisonControllerMobile extends AbstractController
{

    /**
     * @Route("/", name="app_livraison_index_mobile", methods={"GET"})
     */
    public function index(\App\Repository\LivraisonRepository $livraisonRepository, NormalizerInterface $normalizable): Response
    {
        $jsonContent = $normalizable->normalize($livraisonRepository->findAll(), 'json', ['groups' => 'post:read']);

        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);
    }

    /**
     * @Route("/{idC}", name="app_livraison_index_one_mobile", methods={"GET"})
     */
    public function indexOne($idC, \App\Repository\LivraisonRepository $livraisonRepository, NormalizerInterface $normalizable): Response
    {
        $livraison = $livraisonRepository->findBy(['commande' => $idC]);

        $jsonContent = $normalizable->normalize($livraison, 'json', ['groups' => 'post:read']);

        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);
    }

    /**
     * @Route("/{id}/editConfirmation", name="app_livraison_editconfirm_mobile", methods={"GET", "POST"})
     */
    public function editconfrim(\App\Entity\Livraison $livraison, Request $request, \App\Repository\LivraisonRepository $livraisonRepository): Response
    {
        $livraison->setConfirmer(1);

        $livraisonRepository->add($livraison, true);

        return new Response("livraison items Suprimée avec succée", Response::HTTP_OK);

    }


    /**
     * @Route("/{id}", name="app_livraison_delete_mobile", methods={"POST"})
     */
    public function delete(Request $request, \App\Entity\Livraison $livraison, \App\Repository\LivraisonRepository $livraisonRepository): Response
    {
        $livraisonRepository->remove($livraison, true);
        return new Response("livraison items Suprimée avec succée", Response::HTTP_OK);
    }

    /**
     * @Route("/new/{idC}", name="app_livraison_new", methods={"GET", "POST"})
     */
    public function new($idC, Request $request, \App\Repository\LivraisonRepository $livraisonRepository, \App\Repository\CommandeRepository $commandeRepository): Response
    {
        $livraison = new \App\Entity\Livraison();
        $livraison->setAdresse($request->get('adresse'));
        $livraison->setEmail($request->get('email'));

        $livraison->setDate(new \DateTime());
        $livraison->setCommande($commandeRepository->findOneBy(["idC" => $idC]));
        $livraisonRepository->add($livraison, true);

        return new Response('livraison ajout success', Response::HTTP_OK);

    }

    /**
     * @Route("/{idC}/edit", name="app_livraison_edit_mobile", methods={"GET", "POST"})
     */
    public function edit($idC, Request $request, \App\Repository\LivraisonRepository $livraisonRepository): Response
    {
        $livraison = $livraisonRepository->findOneBy(['commande' => $idC]);

        $livraison->setAdresse($request->get('adresse'));
        $livraison->setEmail($request->get('email'));
        $livraisonRepository->add($livraison, true);

        return new Response('livraison ajout success', Response::HTTP_OK);

    }

}
