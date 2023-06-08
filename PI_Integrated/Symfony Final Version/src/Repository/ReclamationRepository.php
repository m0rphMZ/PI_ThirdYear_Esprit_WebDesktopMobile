<?php

namespace App\Repository;

use App\Entity\Reclamation;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Reclamation>
 *
 * @method Reclamation|null find($id, $lockMode = null, $lockVersion = null)
 * @method Reclamation|null findOneBy(array $criteria, array $orderBy = null)
 * @method Reclamation[]    findAll()
 * @method Reclamation[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ReclamationRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Reclamation::class);
    }

    public function save(Reclamation $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Reclamation $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function findByUserId(int $userId): array
    {
        return $this->createQueryBuilder('r')
            ->andWhere('r.user = :userId')
            ->setParameter('userId', $userId)
            ->getQuery()
            ->getResult();
    }

    public function findAllReclamations(): array
    {
        return $this->createQueryBuilder('r')
            ->getQuery()
            ->getResult();
    }

    public function findReclamationsByStatus($status, $userId)
    {
        $qb = $this->createQueryBuilder('r')
            ->where('r.status = :status')
            ->andWhere('r.user = :userId')
            ->setParameter('status', $status)
            ->setParameter('userId', $userId)
            ->orderBy('r.dateCreation', 'DESC');
    
        return $qb->getQuery()->getResult();
    }
    

    public function searchReclamations(string $searchTerm, int $userId): array
            {
                $qb = $this->createQueryBuilder('r')
                    ->where('r.user = :userId')
                    ->andWhere('r.titreRec LIKE :searchTerm OR r.type LIKE :searchTerm OR r.description LIKE :searchTerm OR r.dateCreation LIKE :searchTerm OR r.dateFin LIKE :searchTerm')
                    ->setParameter('userId', $userId)
                    ->setParameter('searchTerm', '%' . $searchTerm . '%')
                    ->orderBy('r.dateCreation', 'DESC');

                return $qb->getQuery()->getResult();
            }







            public function findAllReclamationsBySearchTerm($searchTerm)
{
    $queryBuilder = $this->createQueryBuilder('r')
        ->leftJoin('r.user', 'u')
        ->where('r.description LIKE :searchTerm')
        ->orWhere('r.status LIKE :searchTerm')
        ->orWhere('r.type LIKE :searchTerm')
        ->orWhere('r.dateCreation LIKE :searchTerm')
        ->orWhere('r.dateFin LIKE :searchTerm')
        ->orWhere('u.nom LIKE :searchTerm')
        ->orWhere('u.prenom LIKE :searchTerm')
        ->orWhere('u.email LIKE :searchTerm')
        ->setParameter('searchTerm', '%'.$searchTerm.'%');

    return $queryBuilder->getQuery()->getResult();
}




            







//    /**
//     * @return Reclamation[] Returns an array of Reclamation objects
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

//    public function findOneBySomeField($value): ?Reclamation
//    {
//        return $this->createQueryBuilder('r')
//            ->andWhere('r.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
