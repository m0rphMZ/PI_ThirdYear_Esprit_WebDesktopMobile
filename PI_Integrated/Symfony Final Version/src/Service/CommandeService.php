<?php

namespace App\Service;

class CommandeService
{
    /**
     * Insère ou met à jour une commande dans la base de données.
     *
     * @param \App\Entity\Commande $commande La commande à insérer ou mettre à jour.
     * @param \Doctrine\ORM\EntityManagerInterface $entityManager L'EntityManager pour la gestion des entités.
     * 
     * @return void
     */
    public static function insertOrUpdate(\App\Entity\Commande $commande, \Doctrine\ORM\EntityManagerInterface $entityManager)
    {

        // Récupération de la commande existante dans la base de données
        $dbCommande = $entityManager->find(\App\Entity\Commande::class, $commande->getIdC());

        // Si la commande n'existe pas, on la persiste telle quelle.
        if (!$dbCommande) {
            $dbCommande = $commande;
        } else {
            // Sinon, on met à jour le statut de la commande existante avec celui de la commande donnée.
            $dbCommande->setStatue($commande->getStatue());
        }

        // Persiste la commande dans la base de données.
        $entityManager->persist($dbCommande);
        $entityManager->flush();
    }

    /**
     * Place une commande enregistrée en base de données.
     *
     * @param \App\Entity\Commande $commande La commande à placer.
     * @param CartService1 $cartService Le service de panier utilisé pour la commande.
     * @param \Doctrine\ORM\EntityManagerInterface $entityManager L'EntityManager pour la gestion des entités.
     * 
     * @return \App\Entity\Commande La commande placée.
     */
    public function placeOrder(\App\Entity\Commande $commande, CartService1 $cartService, \Doctrine\ORM\EntityManagerInterface $entityManager)
    {

        // Met à jour le statut de la commande avec "placed"
        $commande->setStatue("placed");

        // Si le total du panier est supérieur à 100, applique une remise de 10%
        if ($cartService->getTotal() > 100) {
            $commande->setRemise(10);
        }

        // Insère ou met à jour la commande dans la base de données.
        self::insertOrUpdate($commande, $entityManager);

        // Vide le panier.
        $cartService->clear();

        // Crée une nouvelle commande pour l'utilisateur avec la date actuelle.
        $newCommande = new \App\Entity\Commande();
        $newCommande->setUser($commande->getUser());
        $date = new \DateTime();
        $newCommande->setDate($date);

        // Persiste la nouvelle commande dans la base de données.
        $entityManager->persist($newCommande);
        $entityManager->flush();

        // Renvoie la commande placée.
        return $commande;
    }
}
