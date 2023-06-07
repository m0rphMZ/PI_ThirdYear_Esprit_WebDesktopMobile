<?php

namespace App\Controller;

use App\Entity\Ticket;
use App\Entity\Event;
use App\Entity\User;
use App\Form\TicketType;
use App\Repository\TicketRepository;
use App\Repository\EventRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

//QRCode & PDF & Pagination Bundle:
use Endroid\QrCode\QrCode;
use Endroid\QrCode\Writer\PngWriter;
use Knp\Bundle\SnappyBundle\Snappy\Response\PdfResponse;
use Nucleos\DompdfBundle\Wrapper\DompdfWrapperInterface;
use Endroid\QrCodeBundle\Response\QrCodeResponse;
use Knp\Component\Pager\PaginatorInterface;


class TicketController extends AbstractController
{   
    //User ticket list:
    #[Route('/ticket', name: 'app_ticket_index', methods: ['GET'])]
    public function index(Request $request, EntityManagerInterface $entityManager, PaginatorInterface $paginator): Response
    {   
        $user_connected=new User();
            $session = $request->getSession();
            $user_connected=$session->get('user');

            $userId = $user_connected->getIdUser();

        $user = $entityManager->getRepository(User::class)->find($userId);
        $tickets = $entityManager
            ->getRepository(Ticket::class)
            ->findBy(['user' => $user]);

            $pagination = $paginator->paginate(
                $tickets,
                $request->query->getInt('page', 1),
                4
            );

        return $this->render('ticket/index.html.twig', [
            'tickets' => $pagination,
        ]);

    }

    //Admin Ticket List:
    #[Route('/admin/ticketindex', name: 'app_ticket_index_admin', methods: ['GET'])]
    public function adminIndex(Request $request, EntityManagerInterface $entityManager, PaginatorInterface $paginator): Response
    {
        $tickets = $entityManager
            ->getRepository(Ticket::class)
            ->findAll();

            $pagination = $paginator->paginate(
                $tickets,
                $request->query->getInt('page', 1),
                4
            );

        return $this->render('ticket/admin_index.html.twig', [
            'tickets' => $pagination,
        ]);
    }

    //Admin create Ticket:
    #[Route('/ticket/newTicket', name: 'app_ticket_new_admin', methods: ['GET', 'POST'])]
    public function new(Request $request, TicketRepository $ticketRepository, EventRepository $eventRepository): Response
    {
        $user_connected=new User();
            $session = $request->getSession();
            $user_connected=$session->get('user');
        $ticket = new Ticket();
        $form = $this->createForm(TicketType::class, $ticket, [
            'event' => $this->getDoctrine()->getRepository(Event::class)->findAll(),
            'user' => $this->getDoctrine()->getRepository(User::class)->findAll(),
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $event = $ticket->getEvent();
            // Set Ticket Price
            $ticket->setPrice($event->getTicketprice());

            // Generate QR Code
            // $writer = new PngWriter();
            // $qrCode = QrCode::create('https://www.binaryboxtuts.com/'); // You can use any unique string as the content of the QR code
            // $qrCode->setSize(300);
            // $qrCode->setMargin(10);
            // $qrCodePath = '/img/qrCode'.$ticket->getTicketId().'.png'; // Replace with the path where you want to save the QR code
            // $result = $writer->write($qrCode);
            // $writer->validateResult($result, 'Life is too short to be generating QR codes');
            // $result->saveToFile(__DIR__.'/qrcode.png');
            // $ticket->setQrcodeimg($qrCodePath);
            
            $ticket->setQrcodeimg("qr code img path");

            // Save Ticket
            $userId = $user_connected->getIdUser();
            $entityManager = $this->getDoctrine()->getManager();
            $user = $entityManager->getRepository(User::class)->find($userId);
            $ticket->setUser($user);
            $ticketRepository->save($ticket, true);

            // Update Event ticket count
            $event = $ticket->getEvent();
            $event->setTicketcount($event->getTicketcount() - 1);
            $eventRepository->save($event, true);

            return $this->redirectToRoute('app_ticket_index_admin', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('ticket/new.html.twig', [
            'ticket' => $ticket,
            'form' => $form,
        ]);
    }

    //Buy Ticket
    #[Route('/ticket/new/{id}', name: 'app_ticket_new', methods: ['GET', 'POST'])]
    public function buyTicket($id, EntityManagerInterface $entityManager): Response
    {
        $event = $entityManager->getRepository(Event::class)->find($id);
        $ticket = new Ticket();
        $ticket->setPrice($event->getTicketprice());
        $ticket->setEvent($event);
        $user = $entityManager->getRepository(User::class)->find(1);
        $ticket->setUser($user);
        $ticket->setQrcodeimg('your_qr_code_image_path');

        // decrease the remaining tickets count for the event
        $event->setTicketcount($event->getTicketcount() - 1);

        $entityManager->persist($ticket);
        $entityManager->persist($event);
        $entityManager->flush();

        return $this->redirectToRoute('app_event_show', ['eventId' => $id], Response::HTTP_SEE_OTHER);
    }

    #[Route('/ticket/{ticketId}', name: 'app_ticket_show', methods: ['GET'])]
    public function show(Ticket $ticket): Response
    {
        return $this->render('ticket/show.html.twig', [
            'ticket' => $ticket,
        ]);
    }
	
	
    #[Route('/ticket/{ticketId}/edit', name: 'app_ticket_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Ticket $ticket, TicketRepository $ticketRepository, EventRepository $eventRepository): Response
    {
        $oldTicket = $ticket->getEvent()->getEventId();
        $form = $this->createForm(TicketType::class, $ticket, [
            'event' => $this->getDoctrine()->getRepository(Event::class)->findAll(),
            'user' => $this->getDoctrine()->getRepository(User::class)->findAll(),
        ]);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            var_dump($oldTicket);
            var_dump($ticket->getEvent()->getEventId());
                
                //Increase old event ticket count
                $oldEvent = $eventRepository->find($oldTicket);
                $oldEvent->setTicketcount($oldEvent->getTicketcount() + 1);
                // $eventRepository->save($oldEvent, true);

                $newEvent = $eventRepository->find($form->get('event')->getData()->geteventId());
                $newEvent->setTicketcount($newEvent->getTicketcount() - 1);
                $eventRepository->save($newEvent, true);
                
                // Set Ticket Price
                $ticket->setPrice($newEvent->getTicketprice());

            // Save Ticket
            $ticketRepository->save($ticket, true);

            return $this->redirectToRoute('app_ticket_index_admin', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('ticket/edit.html.twig', [
            'ticket' => $ticket,
            'form' => $form,
        ]);
    }
	
	
    #[Route('/ticket/{ticketId}', name: 'app_ticket_delete', methods: ['POST'])]
    public function delete(Request $request, Ticket $ticket, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$ticket->getTicketId(), $request->request->get('_token'))) {
            $entityManager->remove($ticket);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_ticket_index_admin', [], Response::HTTP_SEE_OTHER);
    }

    //PDF Bundle Code
    #[Route('/ticket/print/{ticketId}', name: 'app_ticket_print')]
    public function printAction(Ticket $ticket, DompdfWrapperInterface $dompdfWrapper)
    {
        
        $html = $this->renderView('ticket/print.html.twig', array(
            'ticket' => $ticket
        ));
        $response = $dompdfWrapper->getStreamResponse($html, "document.pdf");
        $response->headers->set('Content-Type', 'application/pdf');

    return $response;
    }

    //QRCode Generator:
    public function qrCodeAction()
{
    $text = 'https://example.com';
    $qrCode = $this->get('endroid.qr_code')->create($text);

    return new QrCodeResponse($qrCode);
}
}
