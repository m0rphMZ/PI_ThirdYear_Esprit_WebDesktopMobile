<?php

namespace App\Controller;

use Stripe\Checkout\Session;
use Stripe\Stripe;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

class PaymentController extends AbstractController
{
    /**
     * @Route("/payment", name="app_payment")
     */
    public function index(): Response
    {
        return $this->render('payment/index.html.twig', [
            'controller_name' => 'PaymentController',
        ]);
    }

    /**
     * @Route("/checkout", name="app_checkout")
     */
    public function checkout(Request $request): Response
    {

        Stripe::setApiKey('sk_test_51Mzg2dKp9P2IwbgSp2yTRhqOPFbolK5qk54hhP5A4kEtaurZqvCKAf4ppCxjEa3OT1hLZcP6hhnqY8hK30QkMoNU00p7njd5Jh');

        $session = Session::create([
            'payment_method_types' => ['card'],
            'line_items' => [
                [
                    'price_data' => [
                        'currency' => 'usd',
                        'product_data' => [
                            'name' => 'T-shirt',
                        ],
                        'unit_amount' => 2000,
                    ],
                    'quantity' => 1,
                ]
            ],
            'mode' => 'payment',
            'success_url' => $this->generateUrl('app_payment_success', [], UrlGeneratorInterface::ABSOLUTE_URL),
            'cancel_url' => $this->generateUrl('app_payment_cancel', [], UrlGeneratorInterface::ABSOLUTE_URL),
        ]);

        return $this->redirect($session->url, 303);
    }


    /**
     * @Route("/success", name="app_payment_success")
     */
    public function successUrl(): Response
    {
        $this->addFlash('success', 'payment effectuée');
        return $this->render('payment/success.html.twig', []);
    }


    /**
     * @Route("/cancel", name="app_payment_cancel")
     */
    public function cancelUrl(): Response
    {
        $this->addFlash('fail', 'payment non effectuée');

        return $this->render('payment/cancel.html.twig', []);
    }
}