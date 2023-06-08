<?php

namespace App\Service;

use App\Entity\OrderItems;
use App\Repository\OrdersItemsRepository;
use Doctrine\ORM\EntityManagerInterface;

class CommandeItemService
{

    public static function insertOrUpdate(\App\Entity\CommandeItem $item, \App\Repository\CommandeItemRepository $commandeItemRepository, EntityManagerInterface $entityManager){

        $dbItem = $commandeItemRepository->findOneBy(['commande'=>$item->getCommande(),'produit'=>$item->getProduit(),]);
        if (!$dbItem) {
            $dbItem=$item;
        }
        else
            $dbItem->setQuantity($item->getQuantity());

        $entityManager->persist($dbItem);
        $entityManager->flush();
    }

    public static function delete(\App\Entity\CommandeItem $item,\App\Repository\CommandeItemRepository $commandeItemRepository, EntityManagerInterface $entityManager){

        $entityManager->remove($commandeItemRepository->findOneBy(['commande'=>$item->getCommande(),'produit'=>$item->getProduit()]));
        $entityManager->flush();
    }
}