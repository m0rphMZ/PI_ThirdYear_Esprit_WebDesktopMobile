<?php

namespace App\Controller;

use ApiPlatform\Api\UrlGeneratorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Ticket;
use App\Entity\Event;
use App\Entity\User;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\ORM\EntityManagerInterface;

use Stripe\Stripe;
use Endroid\QrCode\QrCode;
use Symfony\Component\DependencyInjection\ParameterBag\ParameterBagInterface;

use Endroid\QrCodeBundle\Response\QrCodeResponse;
use Endroid\QrCode\Writer\PngWriter;

class TicketPaymentController extends AbstractController
{
    private $qrCodeDir;

    public function __construct(ParameterBagInterface $params)
    {
        $this->qrCodeDir = $params->get('kernel.project_dir') . '/public/img/qrcode';
    }

    #[Route('/purtic', name: 'app_ticket_purtic')]
    public function index(): Response
    {
        return $this->render('ticket_payment/index.html.twig', [
            'controller_name' => 'TicketPaymentController',
        ]);
    }

    #[Route('/checkouttic/{id}', name: 'app_ticket_checkout_tic')]
    public function checkout(Event $event): Response
    {
        $stripe = new \Stripe\StripeClient('sk_test_51MgtelLLCNeC0hr0mCf88VawiUWkhHUT31HrUOJieGeZXcph8jTHoXYvULZB8micyWQVupMyHx5meCHom3D80TVA00roslqe44');

        $checkout_session = $stripe->checkout->sessions->create([
            'line_items' => [[
              'price_data' => [
                'currency' => 'usd',
                'product_data' => [
                  'name' => 'Ticket purchase',
                ],
                'unit_amount' => ($event->getTicketprice())*100,
              ],
              'quantity' => 1,
            ]],
            'mode' => 'payment',
            'success_url' => $this->generateUrl('success_url', ['eventId' => $event->getEventId()], UrlGeneratorInterface::ABS_URL),
            'cancel_url' => $this->generateUrl('cancel_url', [], UrlGeneratorInterface::ABS_URL),
          ]);

        //return $response->withHeader('Location', $session->url)->withStatus(303);
        return $this->redirect($checkout_session->url, 303);
    }

    #[Route('/success-url', name: 'success_url')]
    public function successUrl(Request $request, EntityManagerInterface $entityManager): Response
    {
        $user_connected=new User();
            $session = $request->getSession();
            $user_connected=$session->get('user');

        $eventId = $request->query->get('eventId');
        $event = $entityManager->getRepository(Event::class)->find($eventId);

        $userId = $user_connected->getIdUser();
        $ticket = new Ticket();
        $ticket->setPrice($event->getTicketprice());
        $ticket->setEvent($event);
        $user = $entityManager->getRepository(User::class)->find($userId);
        $ticket->setUser($user);

        //QR Code:
        // $renderer = new ImageRenderer(
        //     new RendererStyle(400),
        //     new ImagickImageBackEnd()
        // );
        // $qrCode = new Writer($renderer);
        // $qrCodeData = "Event: " . $event->getTitle() . "\nTicket price: " . $ticket->getPrice();
        // $imagePath = $this->getParameter('kernel.project_dir') . '/public/img/qrcodetic';
        // $qrCode->writeFile($qrCodeData, $imagePath . $ticket->getTicketId() . '.png');

        // // Set the QR code image path on the ticket entity
        // $ticket->setQrcodeimg('img/tickets/' . $ticket->getTicketId() . '.png');
        $qrCodeData = "Event: " . $event->getTitle() . "\nTicket price: " . $ticket->getPrice() . "\nOwner: " . $user->getNom();;
        $ticket->setQrCodeImg($this->generateQrCode($qrCodeData));
        //$ticket->setQrcodeimg('QR Code');

        // decrease the remaining tickets count for the event
        $event->setTicketcount($event->getTicketcount() - 1);

        $entityManager->persist($ticket);
        $entityManager->persist($event);
        $entityManager->flush();


        return $this->render('ticket_payment/success.html.twig', [
            'controller_name' => 'TicketPaymentController',
        ]);
    }

    #[Route('/cancel-url', name: 'cancel_url')]
    public function cancelUrl(): Response
    {
        return $this->render('ticket_payment/cancel.html.twig', [
            'controller_name' => 'TicketPaymentController',
        ]);
    }

    public function generateQrCode(string $data): string
    {
        $writer = new PngWriter();
        $qrCode = new QrCode($data);
        $qrCode->setSize(256);
        $qrCode->setMargin(10);
        $fileName = uniqid() . '.png';
        $qrCodePath = $this->qrCodeDir . '/' . $fileName;
        if (!file_exists($this->qrCodeDir)) {
            mkdir($this->qrCodeDir, 0755, true);
        }
        $result = $writer->write($qrCode);
        $result->saveToFile($qrCodePath);
        //$qrDBImgPath = '/public/img/qrcode/' . $filName;
        return $fileName;
    }

}
