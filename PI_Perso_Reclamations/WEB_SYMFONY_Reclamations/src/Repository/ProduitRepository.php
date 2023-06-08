<?php
namespace App\Repository;
use App\Entity\Produit;
use Doctrine\Persistence\ManagerRegistry;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;

class ProduitRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Produit::class);
    }

    public function search($results1 = null){
        $query = $this->createQueryBuilder('a');
       // $query->where('a.active = 1');
        // if($mots != null){
        //     $query->andWhere('MATCH_AGAINST(a.title, a.content) AGAINST (:mots boolean)>0')
        //         ->setParameter('mots', $mots);
        // }
        // if($categorie != null){
        //     $query->leftJoin('a.categories', 'c');
        //     $query->andWhere('c.id = :id')
        //         ->setParameter('id', $categorie);
        // }
        if($results1 != null){
            $query->andWhere('a.idcat IN(:cats)')
                ->setParameter(':cats', array_values($results1));
        }

        return $query->getQuery()->getResult();
    }
    public function recherchee($value): array
    {
        $qb= $this->createQueryBuilder('c')
            ->andWhere('c.codeproduit LIKE :val')
            ->setParameter('val', $value);
           
         
         return $qb->getQuery()->getResult();
        
    }
    public function trie(): array
    {
        $qb= $this->createQueryBuilder('c')
        ->orderBy('c.prixunitaire', 'ASC');
         return $qb->getQuery()->getResult();
        
    }



}