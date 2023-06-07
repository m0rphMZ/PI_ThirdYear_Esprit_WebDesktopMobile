<?php

namespace App\Controller;

use App\Entity\User;
use App\Entity\Event;
use App\Form\EventType;
use App\Entity\Location;
use App\Repository\EventRepository;
use App\Repository\TicketRepository;
use App\Repository\LocationRepository;
use App\Controller\CommentaireController;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\HttpFoundation\JsonResponse;

use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\Form\Extension\Core\Type\FileType;

//Commentaire Integration step1:
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;


class TicketJSONController extends AbstractController
{
    //Ticket List
    #[Route('/ticketlist', name: 'app_ticket_json_index', methods: ['GET', 'POST'])]
    public function jsonIndex(Request $request, NormalizerInterface $Normalizer, TicketRepository $ticketRepository)
    {
        $tickets = $ticketRepository->findAll();
        $ticketsNorm = $Normalizer->normalize($tickets, 'json');

        $json = json_encode($ticketsNorm);

        return new Response($json);
    }
    
}