<?php

namespace App\Controller;

use App\Entity\User;
use App\Entity\Commande;
use App\Form\CommandeType;
use App\Repository\CommandeRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Knp\Component\Pager\PaginatorInterface;


/**
 * @Route("/mobile/commande")
 */
class CommandeControllerMobile extends AbstractController
{
    /**
     * @Route("/", name="app_commande_index_mobile", methods={"GET"})
     */
    public function index(CommandeRepository $commandeRepository, NormalizerInterface $normalizable): Response
    {
        $jsonContent = $normalizable->normalize($commandeRepository->findAll(), 'json', ['groups' => 'post:read']);

        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);

    }

    /**
     * @Route("/mes/Commandes", name="mesCommande_mobile", methods={"GET"})
     */
    public function mesCommande(Request $request,CommandeRepository $commandeRepository, NormalizerInterface $normalizable,PaginatorInterface $paginator): Response
    {
        



        $userId = $request->get('id');
      
      $commande = $commandeRepository->findBy([
        'user' => $userId
    ]);
    
        
        $jsonContent = $normalizable->normalize($commande, 'json', ['groups' => 'post:read']);

        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);

    }


    /**
     * @Route("/{idC}", name="app_commande_show_mobile", methods={"GET"})
     */
    public function show(Commande $commande): Response
    {
        return $this->render('commande/show.html.twig', [
            'commande' => $commande,
        ]);
    }


    /**
     * @Route("/delete/{idC}", name="app_commande_delete_mobile", methods={"POST"})
     */
    public function delete(Request $request, Commande $commande, CommandeRepository $commandeRepository): Response
    {
        
        $commandeRepository->remove($commande, true);
        return new Response("commande Suprimée avec succée", Response::HTTP_OK);

    }

    /**
     * @Route("/orderCommande", name="orderCommande_mobile")
     */
    public function placeOrderm(\App\Service\CartService1 $cartService, \App\Service\CommandeService $commandeService)
    {
        $commandeService->placeOrder($cartService->getCurrentOrder(), $cartService, $this->getDoctrine()->getManager());
        return new Response("commande order success", Response::HTTP_OK);
    }
}
