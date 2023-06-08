<?php

namespace App\Repository;

use App\Entity\Reponses;
use App\Entity\Reclamation;

use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Reponses>
 *
 * @method Reponses|null find($id, $lockMode = null, $lockVersion = null)
 * @method Reponses|null findOneBy(array $criteria, array $orderBy = null)
 * @method Reponses[]    findAll()
 * @method Reponses[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ReponsesRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Reponses::class);
    }

    public function save(Reponses $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Reponses $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
    public function findResponsesByReclamation(Reclamation $reclamation)
{
    return $this->createQueryBuilder('r')
        ->andWhere('r.rec = :reclamation')
        ->setParameter('reclamation', $reclamation)
        ->orderBy('r.dateRep', 'DESC')
        ->getQuery()
        ->getResult()
    ;
}










//    /**
//     * @return Reponses[] Returns an array of Reponses objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('r')
//            ->andWhere('r.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('r.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Reponses
//    {
//        return $this->createQueryBuilder('r')
//            ->andWhere('r.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
