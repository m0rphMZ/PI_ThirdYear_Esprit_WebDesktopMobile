<?php

namespace App\Service;

use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\RequestStack;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Psr\Log\LoggerInterface;


class CartService1
{

    private $logger;

    protected $session;
    protected $produitRepository;
    protected $commandeRepo;
    protected $commandeitemRepo;
    protected $entityManager;
    protected $request;

    public function __construct(LoggerInterface $logger, SessionInterface $session, \App\Repository\ProduitRepository $produitRepository, \App\Repository\CommandeRepository $commandeRepository, \App\Repository\CommandeItemRepository $commandeItemRepository, EntityManagerInterface $entityManager, RequestStack $requestStack)
    {
        $this->logger = $logger;

        $this->session = $session;
        $this->produitRepository = $produitRepository;
        $this->commandeRepo = $commandeRepository;
        $this->commandeitemRepo = $commandeItemRepository;
        $this->entityManager = $entityManager;
        $this->request = $requestStack->getCurrentRequest();
    }


    
    public function remove(int $id)
    {

        $cart = $this->session->get('cart', []);

        $commandeitem = new \App\Entity\CommandeItem();
        $commandeitem->setQuantity($cart[$id])
            ->setProduit($this->produitRepository->find($id))
            ->setCommande($this->getCurrentOrder());
        CommandeItemService::delete($commandeitem, $this->commandeitemRepo, $this->entityManager);

        if (!empty($cart[$id]))
            unset($cart[$id]);
        $this->session->set('cart', $cart);
        $this->session->migrate();
    }


    public function add(int $id)
    {

        $cart = $this->session->get('cart', []);
        if (!empty($cart[$id]))
            $cart[$id]++;
        else
            $cart[$id] = 1;

        $commandeItem = new \App\Entity\CommandeItem();
        $commandeItem->setQuantity($cart[$id])
            ->setProduit($this->produitRepository->find($id))
            ->setCommande($this->getCurrentOrder());


        CommandeItemService::insertOrUpdate($commandeItem, $this->commandeitemRepo, $this->entityManager);
        $this->session->set('cart', $cart);

    }


    public function clear()
    {
        $this->session->set('cart', []);

    }


    // public function getCurrentOrder(): \App\Entity\Commande
    // {
    //     $user_connected = $this->session->get('user');
    //     $userId = $user_connected->getIdUser();
    //     return $this->session->get('currentOrder', $this->commandeRepo->findOneBy([
    //         'user' => $userId
            
    //     ]));
    // }

    public function getCurrentOrder(): \App\Entity\Commande
    {
        $user_connected = $this->session->get('user');
        $userId = $user_connected->getIdUser();
    
        $commande = $this->commandeRepo->findOneBy([
            'user' => $userId
        ]);
    
        if (!$commande) {
            // If no command exists for the user, create a new one
            $commande = new \App\Entity\Commande();
            $commande->setUser((string) $userId); // Cast the user ID to a string
            $commande->setDate(new \DateTime());
            $this->entityManager->persist($commande);
            $this->entityManager->flush();
        }
    
        $this->session->set('currentOrder', $commande);
    
        return $commande;
    }
    







    

    public function getCart(): array
    {

        $cart = $this->session->get('cart', []);


        if (!$cart)
            return $this->initCart();

        $cartWithData = [];
        foreach ($cart as $id => $quantity) {
            $cartWithData[] = [
                'product' => $this->produitRepository->find($id),
                'quantity' => $quantity
            ];
        }
        return $cartWithData;
    }

    public function getTotal(): float
    {

        $total = 0;
        foreach ($this->getCart() as $item)
            $total += $item['product']->getPrixunitaire() * $item['quantity'];

        return $total;
    }

    public function initCart()
    {

        $cartWithData = [];
        $item = new \App\Entity\CommandeItem();
        foreach ($this->commandeitemRepo->findBy(['commande' => $this->getCurrentOrder()]) as $item) {
            $cartWithData[] = [
                'product' => $item->getProduit(),
                'quantity' => $item->getQuantity()
            ];
            $cart[$item->getProduit()->getId()] = $item->getQuantity();
            $this->session->set('cart', $cart);
        }
        return $cartWithData;
    }


}