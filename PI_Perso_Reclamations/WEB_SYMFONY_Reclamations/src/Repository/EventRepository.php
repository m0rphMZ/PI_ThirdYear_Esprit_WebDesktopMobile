<?php

namespace App\Repository;

use App\Entity\Event;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Event>
 *
 * @method Event|null find($id, $lockMode = null, $lockVersion = null)
 * @method Event|null findOneBy(array $criteria, array $orderBy = null)
 * @method Event[]    findAll()
 * @method Event[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class EventRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Event::class);
    }

    public function save(Event $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Event $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function findEventByTitle(string $searchTerm): array
{
    $qb = $this->createQueryBuilder('e');

        if ($searchTerm) {
            $qb->andWhere('e.title LIKE :searchTerm')
                ->setParameter('searchTerm', '%'.$searchTerm.'%');
        }

        return $qb->getQuery()->getResult();
}

public function search($criteria, $sortCriteria, $order)
{
    $qb = $this->createQueryBuilder('e');

    if (!empty($criteria['title'])) {
        $qb->andWhere('e.title LIKE :title')
            ->setParameter('title', '%'.$criteria['title'].'%');
    }

    if (!empty($criteria['type'])) {
        $qb->andWhere('e.type = :type')
            ->setParameter('type', $criteria['type']);
    }

    if (!empty($criteria['startDate'])) {
        $qb->andWhere('e.startdate >= :startDate')
            ->setParameter('startDate', $criteria['startDate']);
    }

    if (!empty($criteria['endDate'])) {
        $qb->andWhere('e.enddate <= :endDate')
            ->setParameter('endDate', $criteria['endDate']);
    }

    if (!empty($criteria['minPrice'])) {
        $qb->andWhere('e.ticketprice >= :minPrice')
            ->setParameter('minPrice', $criteria['minPrice']);
    }

    if (!empty($criteria['maxPrice'])) {
        $qb->andWhere('e.ticketprice <= :maxPrice')
            ->setParameter('maxPrice', $criteria['maxPrice']);
    }

    // add sorting criteria to query builder
    $qb->orderBy($sortCriteria, $order);

    return $qb->getQuery()->getResult();
}

//    /**
//     * @return Event[] Returns an array of Event objects
//     */
//    public function findEventByTitle($value): array
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.title = :val')
//            ->setParameter('val', $value)
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Event
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
