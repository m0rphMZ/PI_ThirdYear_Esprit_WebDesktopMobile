<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Notifier\Notification\Notification;
use Symfony\Component\Notifier\Bridge\Slack\Block\SlackDividerBlock;
use Symfony\Component\Notifier\Bridge\Slack\Block\SlackSectionBlock;
use Symfony\Component\Notifier\Bridge\Slack\SlackOptions;
use Symfony\Component\Notifier\Message\ChatMessage;


use Symfony\Component\Notifier\Notifier;

use Symfony\Component\Notifier\NotifierInterface;
use Symfony\Component\Notifier\Recipient\Recipient;
use Symfony\Component\Notifier\Transport\TransportInterface;
use Symfony\Component\Notifier\Factory\SlackWebhookNotifierFactory;


use Symfony\Component\Notifier\ChatterInterface;

class InterestController extends AbstractController
{
    #[Route('/interest', name: 'app_interest')]
    public function index(): Response
    {
        return $this->render('interest/index.html.twig', [
            'controller_name' => 'InterestController',
        ]);
    }
    #[Route('/checkout/thankyou', name: 'thankyou')]


    public function thankyou(ChatterInterface $chatter)
    {
       /* $message = (new ChatMessage('hahahhahahahhahahahhahahhahahah'))
            // if not set explicitly, the message is sent to the
            // default transport (the first one configured)
          
            ->transport('slack');

        $sentMessage = $chatter->send($message);
        
        return new Response("succes"); 
        

*/


$dsn = Dsn::fromString($_ENV['SLACK_DSN']);
$transport = (new SlackTransportFactory())->create($dsn);


$message = (new ChatMessage('Hello, Slack!'))->transport($transport);
$chatter = new Chatter([$transport]);
$sentMessage = $chatter->send($message);

    }
    
    /*#[Route('/send-slack-message', name: 'send-slack-message',methods: ['POST'])]
    
    public function sendSlackMessage(NotifierInterface $notifier): Response
    { 
      
    $message = (new ChatMessage('You got a new invoice for 15 EUR.'))
    // if not set explicitly, the message is sent to the
    // default transport (the first one configured)
    ->transport('slack');

$sentMessage = $chatter->send($message);
return new Response("aaaa"); 
      }*/


}
