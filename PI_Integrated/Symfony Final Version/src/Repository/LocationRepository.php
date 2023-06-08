<?php

namespace App\Repository;

use App\Entity\Location;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Location>
 *
 * @method Location|null find($id, $lockMode = null, $lockVersion = null)
 * @method Location|null findOneBy(array $criteria, array $orderBy = null)
 * @method Location[]    findAll()
 * @method Location[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class LocationRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Location::class);
    }

    public function save(Location $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Location $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function recherche($value): array
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.lieuLoc = :val')
            ->setParameter('val', $value)
            ->orderBy('c.numLoc', 'ASC')
            ->getQuery()
            ->getResult();
    }

//    /**
//     * @return Location[] Returns an array of Location objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('l')
//            ->andWhere('l.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('l.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Location
//    {
//        return $this->createQueryBuilder('l')
//            ->andWhere('l.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }



public function search(array $criteria, string $sortCriteria, string $order): array
{
    $qb = $this->createQueryBuilder('e');

    if (!empty($criteria['lieu_loc'])) {
        $qb->andWhere('e.lieuLoc LIKE :lieu_loc')
            ->setParameter('lieu_loc', '%'.$criteria['lieu_loc']);
    }

    if (!empty($criteria['disponibilite'])) {
        $qb->andWhere('e.disponibilite = :disponibilite')
            ->setParameter('disponibilite', $criteria['disponibilite']);
    }

    
if (!empty($criteria['prix_loc'])) {
   
    $qb->andWhere('e.prixLoc = :prix_loc')
    ->setParameter('prix_loc', $criteria['prix_loc']);
}


    // add sorting criteria to query builder
    if ($sortCriteria == 'e.lieu_loc') {
        $sortCriteria = 'e.lieuLoc';
    }

    if ($sortCriteria == 'e.surface_loc') {
        $sortCriteria = 'e.surfaceLoc';
    }



    if ($sortCriteria == 'e.prix_loc') {
        $sortCriteria = 'e.prixLoc';

   
     
       
   
}
$qb->orderBy($sortCriteria, $order);

return $qb->getQuery()->getResult();
}
}