<?php

namespace App\Service;


use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;

class MailerService
{

    public function __construct(private MailerInterface $mailer )
    {
        $this->mailer=$mailer;
    }

    public function sendEmail(string $to, string $subject, string $body): void
{
    $email = (new Email())
        ->from('mohamed.hmaidi@esprit.tn')
        ->to($to)
        ->subject($subject)
        ->text($body);

    $this->mailer->send($email);
}
}
