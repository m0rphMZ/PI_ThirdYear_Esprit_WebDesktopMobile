<?php

namespace App\Controller;

use App\Entity\Event;
use App\Entity\User;
use App\Entity\Location;
use App\Form\EventType;
use App\Repository\EventRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

use Symfony\Component\HttpFoundation\JsonResponse;
use Knp\Component\Pager\PaginatorInterface;

//Commentaire Integration step1:
use App\Controller\CommentaireController;
use App\Repository\LocationRepository;


class EventJSONController extends AbstractController
{
    //Event List
    #[Route('/eventlist', name: 'app_event_json_index', methods: ['GET', 'POST'])]
    public function jsonIndex(Request $request, NormalizerInterface $Normalizer, EventRepository $eventRepository)
    {
        $events = $eventRepository->findAll();
        $eventsNorm = $Normalizer->normalize($events, 'json');

        $json = json_encode($eventsNorm);

        return new Response($json);
    }

    // Add Event
    #[Route('/eventnew', name: 'app_event_json_new')]
    public function jsonNew(Request $request, NormalizerInterface $Normalizer, EventRepository $eventRepository)
    {
        $event = new Event();
        $event->setTitle($request->get('title'));
        $event->setType($request->get('type'));
        $event->setDescription($request->get('desc'));
        $event->setTicketcount($request->get('tCount'));
        $event->setTicketprice($request->get('tPrice'));

        $startDateString = $request->get('startDate');
        $endDateString = $request->get('endDate');
        $format = 'd/m/Y';
        $startDate = \DateTime::createFromFormat($format, $startDateString);
        $endDate = \DateTime::createFromFormat($format, $endDateString);
        
        $event->setStartdate($startDate);
        $event->setEnddate($endDate);
        $event->setAffiche("");

        $entityManager = $this->getDoctrine()->getManager();
        
        $userId = $request->get('host');
        $user = $entityManager->getRepository(User::class)->find($userId);
        if (!$user) {
            return new Response('User not found', Response::HTTP_NOT_FOUND);
        }
        
        $event->setHost($user);

        $location = $entityManager->getRepository(Location::class)->find(1);
        $event->setLocation($location);
        
        $eventRepository->save($event, true);

        // $json = $Normalizer->normalize($event, 'json');
        // return new Response(json_encode($json));
        $jsonContent = $Normalizer->normalize($event, 'json', ['groups' => 'events']);
        return new Response(json_encode($jsonContent));
    }

    // Show Event
    #[Route('/eventshow/{id}', name: 'app_event_json_show', methods: ['GET', 'POST'])]
    public function jsonShow($id, EventRepository $eventRepository, NormalizerInterface $normalizer)
    {
        $event= $eventRepository->find($id);
        $eventNorm =  $normalizer->normalize($event,'json');
        $json=json_encode($eventNorm);
        return New Response($json);
    }

    #[Route('/eventupdate/{id}', name: 'app_event_json_update')]
    public function jsonUpdate($id, Request $request, NormalizerInterface $normalizer)
    {        
        $em= $this->getDoctrine()->getManager();
        $event=$em->getRepository(Event::class)->find($id);

        $event->setTitle($request->get('title'));
        $event->setType($request->get('type'));
        $event->setDescription($request->get('desc'));
        $event->setTicketcount($request->get('tCount'));
        $event->setTicketprice($request->get('tPrice'));
        $startDateString = $request->get('startDate');
        $endDateString = $request->get('endDate');
        $format = 'd/m/Y';
        $startDate = \DateTime::createFromFormat($format, $startDateString);
        $endDate = \DateTime::createFromFormat($format, $endDateString);
        $event->setStartdate($startDate);
        $event->setEnddate($endDate);
        //$event->setAffiche("");
        $em->flush();
        $jsonContent = $normalizer->normalize($event, 'json');
        return new Response("Success".json_encode($jsonContent));
    }

    #[Route('/eventdelete/{id}', name: 'app_event_json_delete')]
    public function jsonDelete($id, Request $request, NormalizerInterface $normalizer)
    {
        $em= $this->getDoctrine()->getManager();
        $event=$em->getRepository(Event::class)->find($id);
        $em->remove($event);
        $em->flush();
        $jsonContent = $normalizer->normalize($event, 'json');
        return new Response("Success".json_encode($jsonContent));
    }
    
    //Location List Needed for the creation of the event
    #[Route('/eventlocal', name: 'app_event_json_local_index', methods: ['GET'])]
    public function jsonLocalIndex(Request $request, NormalizerInterface $Normalizer, LocationRepository $localRepository)
    {
        $locations = $localRepository->findAll();
        $locationsNorm = $Normalizer->normalize($locations, 'json');
    
        $json = json_encode($locationsNorm);
    
        return new Response($json);
    }
    
}