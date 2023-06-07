<?php

namespace App\Controller;

use Dompdf\Dompdf;
use Stripe\Stripe;
use App\Entity\User;
use App\Entity\Commande;
use App\Form\CommandeType;
use Stripe\Checkout\Session;
use Symfony\Component\Mime\Email;
use App\Repository\CommandeRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

/**
 * @Route("/commande")
 */
class CommandeController extends AbstractController
{
    /**
     * @Route("/invoice/show/{id}", name="app_livraison_invoive", methods={"GET"})
     */
    public function invoice($id, CommandeRepository $commandeRepository): Response
    {
        $totale = 0;
        $commande = $commandeRepository->findOneByIdC($id);
        foreach ($commande->getOrderItem() as $item) {
            $totale = $totale + $item->getQuantity() * $item->getProduit()->getPrix();
        }
        $totale = $totale - ($totale * ($commande->getRemise() / 100));
        $pdfOptions = new \Dompdf\Options();
        $pdfOptions->set('isHtml5ParserEnabled', true);
        $pdfOptions->set('isRemoteEnabled', true);

        $dompdf = new Dompdf($pdfOptions);
        $html = $this->renderView('commande/invoice.html.twig', array('commande' => $commande,
            'totale' => $totale));

        $dompdf->loadHtml($html);
        $dompdf->render();
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => true
        ]);
        exit(0);
    }






    /**
     * @Route("/", name="app_admin_commande", methods={"GET"})
     */
    public function index(Request $request, CommandeRepository $commandeRepository, PaginatorInterface $paginator): Response
    {
        $rechercheString = $request->query->get('rechercheString');

        if ($rechercheString == null) {
            $results = $commandeRepository->findAll(); // Remplacez "searchByTitle" par la méthode que vous utilisez pour rechercher les cours

        } else {
            $results = $commandeRepository->recherche($rechercheString);
        }

        $pagination = $paginator->paginate(
            $results, /* query NOT result */
            $request->query->getInt('page', 1), /*page number*/
            2 /*limit per page*/
        );

        return $this->render('commande/index.html.twig', [
            'commandes' => $pagination,
            'tri' => 'ASC'
        ]);
    }







    #[Route('/tristatute/{tri}', name: 'app_commande_index_tri_statue', methods: ['GET'])]
    public function triadresse($tri, CommandeRepository $commandeRepository, PaginatorInterface $paginator, Request $request): Response
    {
        $commande = $commandeRepository->findBy(array(), array('statue' => $tri));
        if ($tri == 'DESC') {
            $tri = 'ASC';
        } else {
            $tri = 'DESC';
        }
        $pagination = $paginator->paginate(
            $commande,
            $request->query->getInt('page', 1),
            2 /*limit per page*/
        );
        return $this->render('commande/index.html.twig', [
            'commandes' => $pagination,
            'tri' => $tri
        ]);

    }

    #[Route('/tridate/{tri}', name: 'app_commande_index_tri_date', methods: ['GET'])]
    public function tridate($tri, CommandeRepository $commandeRepository, PaginatorInterface $paginator, Request $request): Response
    {
        $commande = $commandeRepository->findBy(array(), array('date' => $tri));
        if ($tri == 'DESC') {
            $tri = 'ASC';
        } else {
            $tri = 'DESC';
        }
        $pagination = $paginator->paginate(
            $commande,
            $request->query->getInt('page', 1),
            2 /*limit per page*/
        );
        return $this->render('commande/index.html.twig', [
            'commandes' => $pagination,
            'tri' => $tri
        ]);

    }











    /**
     * @Route("/mes/Commandes", name="mesCommande", methods={"GET"})
     */
    public function mesCommande(Request $request, CommandeRepository $commandeRepository, \Knp\Component\Pager\PaginatorInterface $paginator): Response
    {
      $user_connected=new User();
      $session = $request->getSession();
      $user_connected=$session->get('user');

      $userId = $user_connected->getIdUser();
      
      $commande = $commandeRepository->findBy([
        'user' => $userId
    ]);
    
        $pagination = $paginator->paginate(
            $commande, /* query NOT result */
            $request->query->getInt('page', 1), /*page number*/
            2 /*limit per page*/
        );
        return $this->render('commande/mesCommande.html.twig', [
            'commandes' => $pagination,
        ]);
    }

    /**
     * @Route("/orderCOmmande", name="orderCommande")
     */
    public function placeOrder(\App\Service\CartService1 $cartService, \App\Service\CommandeService $commandeService, MailerInterface $mailer,Request $request)
    {
        $total = $cartService->getTotal() * 1.12;
        if ($total > 100) {
            $total -= $total * 0.1;
        }

        $user_connected = new User();
        $session = $request->getSession();
        $user_connected = $session->get('user');
        $user = $user_connected->getEmail();


        $order = $cartService->getCurrentOrder();
        $commandeService->placeOrder($order, $cartService, $this->getDoctrine()->getManager());
        $email = (new Email())
            ->from('touskieart.reclamations@gmail.com')
            ->to($user)
            ->subject('Ordre')
            ->html("<!doctype html>
            <html>
            <head>
              <meta name='viewport' content='width=device-width, initial-scale=1.0'/>
              <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
              <title>Simple Transactional Email</title>
              <style>
                /* -------------------------------------
                    GLOBAL RESETS
                ------------------------------------- */
                
                /*All the styling goes here*/
                
                img {
                  border: none;
                  -ms-interpolation-mode: bicubic;
                  max-width: 100%; 
                }
          
                body {
                  background-color: #f6f6f6;
                  font-family: sans-serif;
                  -webkit-font-smoothing: antialiased;
                  font-size: 14px;
                  line-height: 1.4;
                  margin: 0;
                  padding: 0;
                  -ms-text-size-adjust: 100%;
                  -webkit-text-size-adjust: 100%; 
                }
          
                table {
                  border-collapse: separate;
                  mso-table-lspace: 0pt;
                  mso-table-rspace: 0pt;
                  width: 100%; }
                  table td {
                    font-family: sans-serif;
                    font-size: 14px;
                    vertical-align: top; 
                }
          
                /* -------------------------------------
                    BODY & CONTAINER
                ------------------------------------- */
          
                .body {
                  background-color: #f6f6f6;
                  width: 100%; 
                }
          
                /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */
                .container {
                  display: block;
                  margin: 0 auto !important;
                  /* makes it centered */
                  max-width: 580px;
                  padding: 10px;
                  width: 580px; 
                }
          
                /* This should also be a block element, so that it will fill 100% of the .container */
                .content {
                  box-sizing: border-box;
                  display: block;
                  margin: 0 auto;
                  max-width: 580px;
                  padding: 10px; 
                }
          
                /* -------------------------------------
                    HEADER, FOOTER, MAIN
                ------------------------------------- */
                .main {
                  background: #ffffff;
                  border-radius: 3px;
                  width: 100%; 
                }
          
                .wrapper {
                  box-sizing: border-box;
                  padding: 20px; 
                }
          
                .content-block {
                  padding-bottom: 10px;
                  padding-top: 10px;
                }
          
                .footer {
                  clear: both;
                  margin-top: 10px;
                  text-align: center;
                  width: 100%; 
                }
                  .footer td,
                  .footer p,
                  .footer span,
                  .footer a {
                    color: #999999;
                    font-size: 12px;
                    text-align: center; 
                }
          
                /* -------------------------------------
                    TYPOGRAPHY
                ------------------------------------- */
                h1,
                h2,
                h3,
                h4 {
                  color: #000000;
                  font-family: sans-serif;
                  font-weight: 400;
                  line-height: 1.4;
                  margin: 0;
                  margin-bottom: 30px; 
                }
          
                h1 {
                  font-size: 35px;
                  font-weight: 300;
                  text-align: center;
                  text-transform: capitalize; 
                }
          
                p,
                ul,
                ol {
                  font-family: sans-serif;
                  font-size: 14px;
                  font-weight: normal;
                  margin: 0;
                  margin-bottom: 15px; 
                }
                  p li,
                  ul li,
                  ol li {
                    list-style-position: inside;
                    margin-left: 5px; 
                }
          
                a {
                  color: #3498db;
                  text-decoration: underline; 
                }
          
                /* -------------------------------------
                    BUTTONS
                ------------------------------------- */
                .btn {
                  box-sizing: border-box;
                  width: 100%; }
                  .btn > tbody > tr > td {
                    padding-bottom: 15px; }
                  .btn table {
                    width: auto; 
                }
                  .btn table td {
                    background-color: #ffffff;
                    border-radius: 5px;
                    text-align: center; 
                }
                  .btn a {
                    background-color: #ffffff;
                    border: solid 1px #3498db;
                    border-radius: 5px;
                    box-sizing: border-box;
                    color: #3498db;
                    cursor: pointer;
                    display: inline-block;
                    font-size: 14px;
                    font-weight: bold;
                    margin: 0;
                    padding: 12px 25px;
                    text-decoration: none;
                    text-transform: capitalize; 
                }
          
                .btn-primary table td {
                  background-color: #3498db; 
                }
          
                .btn-primary a {
                  background-color: #3498db;
                  border-color: #3498db;
                  color: #ffffff; 
                }
          
                /* -------------------------------------
                    OTHER STYLES THAT MIGHT BE USEFUL
                ------------------------------------- */
                .last {
                  margin-bottom: 0; 
                }
          
                .first {
                  margin-top: 0; 
                }
          
                .align-center {
                  text-align: center; 
                }
          
                .align-right {
                  text-align: right; 
                }
          
                .align-left {
                  text-align: left; 
                }
          
                .clear {
                  clear: both; 
                }
          
                .mt0 {
                  margin-top: 0; 
                }
          
                .mb0 {
                  margin-bottom: 0; 
                }
          
                .preheader {
                  color: transparent;
                  display: none;
                  height: 0;
                  max-height: 0;
                  max-width: 0;
                  opacity: 0;
                  overflow: hidden;
                  mso-hide: all;
                  visibility: hidden;
                  width: 0; 
                }
          
                .powered-by a {
                  text-decoration: none; 
                }
          
                hr {
                  border: 0;
                  border-bottom: 1px solid #f6f6f6;
                  margin: 20px 0; 
                }
          
                /* -------------------------------------
                    RESPONSIVE AND MOBILE FRIENDLY STYLES
                ------------------------------------- */
                @media only screen and (max-width: 620px) {
                  table.body h1 {
                    font-size: 28px !important;
                    margin-bottom: 10px !important; 
                  }
                  table.body p,
                  table.body ul,
                  table.body ol,
                  table.body td,
                  table.body span,
                  table.body a {
                    font-size: 16px !important; 
                  }
                  table.body .wrapper,
                  table.body .article {
                    padding: 10px !important; 
                  }
                  table.body .content {
                    padding: 0 !important; 
                  }
                  table.body .container {
                    padding: 0 !important;
                    width: 100% !important; 
                  }
                  table.body .main {
                    border-left-width: 0 !important;
                    border-radius: 0 !important;
                    border-right-width: 0 !important; 
                  }
                  table.body .btn table {
                    width: 100% !important; 
                  }
                  table.body .btn a {
                    width: 100% !important; 
                  }
                  table.body .img-responsive {
                    height: auto !important;
                    max-width: 100% !important;
                    width: auto !important; 
                  }
                }
          
                /* -------------------------------------
                    PRESERVE THESE STYLES IN THE HEAD
                ------------------------------------- */
                @media all {
                  .ExternalClass {
                    width: 100%; 
                  }
                  .ExternalClass,
                  .ExternalClass p,
                  .ExternalClass span,
                  .ExternalClass font,
                  .ExternalClass td,
                  .ExternalClass div {
                    line-height: 100%; 
                  }
                  .apple-link a {
                    color: inherit !important;
                    font-family: inherit !important;
                    font-size: inherit !important;
                    font-weight: inherit !important;
                    line-height: inherit !important;
                    text-decoration: none !important; 
                  }
                  #MessageViewBody a {
                    color: inherit;
                    text-decoration: none;
                    font-size: inherit;
                    font-family: inherit;
                    font-weight: inherit;
                    line-height: inherit;
                  }
                  .btn-primary table td:hover {
                    background-color: #34495e !important; 
                  }
                  .btn-primary a:hover {
                    background-color: #34495e !important;
                    border-color: #34495e !important; 
                  } 
                }
          
              </style>
            </head>
            <body>
              <span class='preheader'>This is preheader text. Some clients will show this text as a preview.</span>
              <table role='presentation' border='0' cellpadding='0' cellspacing='0' class='body'>
                <tr>
                  <td>&nbsp;</td>
                  <td class='container'>
                    <div class='content'>
          
                      <!-- START CENTERED WHITE CONTAINER -->
                      <table role='presentation' class='main'>
          
                        <!-- START MAIN CONTENT AREA -->
                        <tr>
                          <td class='wrapper'>
                            <table role='presentation' border='0' cellpadding='0' cellspacing='0'>
                              <tr>
                                <td>
                                  <p>Bonjour,</p>
                                  <p>Votre commande a été bien placée.</p>
                                  <table role='presentation' border='0' cellpadding='0' cellspacing='0' class='btn btn-primary'>
                                    <tbody>
                                      <tr>
                                        <td align='left'>
                                          <table role='presentation' border='0' cellpadding='0' cellspacing='0'>
                                            
                                          </table>
                                        </td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
          
                      <!-- END MAIN CONTENT AREA -->
                      </table>
                      <!-- END CENTERED WHITE CONTAINER -->
          
                      <!-- START FOOTER -->
                      <div class='footer'>
                        <table role='presentation' border='0' cellpadding='0' cellspacing='0'>
                          <tr>
                            <td class='content-block'>
                              <span class='apple-link'>ATHlLon Tunisia</span>
                              <br> Don't like these emails? <a href='http://i.imgur.com/CScmqnj.gif'>Unsubscribe</a>.
                            </td>
                          </tr>
                          <tr>
                            <td class='content-block powered-by'>
                            </td>
                          </tr>
                        </table>
                      </div>
                      <!-- END FOOTER -->
          
                    </div>
                  </td>
                  <td>&nbsp;</td>
                </tr>
              </table>
            </body>
          </html>");

        $mailer->send($email);


        $this->addFlash('success', 'commande order success');
        Stripe::setApiKey('sk_test_51Mzg2dKp9P2IwbgSp2yTRhqOPFbolK5qk54hhP5A4kEtaurZqvCKAf4ppCxjEa3OT1hLZcP6hhnqY8hK30QkMoNU00p7njd5Jh');


        $session = Session::create([
            'payment_method_types' => ['card'],
            'line_items' => [
                [
                    'price_data' => [
                        'currency' => 'eur',
                        'product_data' => [
                            'name' => 'test',
                        ],
                        'unit_amount' => $total * 1000,
                    ],
                    'quantity' => 1,
                ]
            ],
            'mode' => 'payment',
            'success_url' => $this->generateUrl('mesCommande', [], UrlGeneratorInterface::ABSOLUTE_URL),
            'cancel_url' => $this->generateUrl('cart', [], UrlGeneratorInterface::ABSOLUTE_URL),
        ]);

        return $this->redirect($session->url, 303);

    }

    /**
     * @Route("/new", name="app_commande_new", methods={"GET", "POST"})
     */
    public function new(Request $request, CommandeRepository $commandeRepository): Response
    {
        $commande = new Commande();
        $form = $this->createForm(CommandeType::class, $commande);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $commandeRepository->add($commande, true);

            return $this->redirectToRoute('app_admin_commande', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('commande/new.html.twig', [
            'commande' => $commande,
            'form' => $form,
        ]);
    }

    /**
     * @Route("/{idC}", name="app_commande_show", methods={"GET"})
     */
    public function show(Commande $commande): Response
    {
        return $this->render('commande/show.html.twig', [
            'commande' => $commande,
        ]);
    }

    /**
     * @Route("/{idC}/edit", name="app_commande_edit", methods={"GET", "POST"})
     */
    public function edit(Request $request, Commande $commande, CommandeRepository $commandeRepository): Response
    {
        $form = $this->createForm(CommandeType::class, $commande);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $commandeRepository->add($commande, true);

            return $this->redirectToRoute('app_admin_commande', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('commande/edit.html.twig', [
            'commande' => $commande,
            'form' => $form,
        ]);
    }

    /**
     * @Route("/{idC}", name="app_commande_delete", methods={"POST"})
     */
    public function delete(Request $request, Commande $commande, CommandeRepository $commandeRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $commande->getIdC(), $request->request->get('_token'))) {
            $commandeRepository->remove($commande, true);
        }

        return $this->redirectToRoute('app_admin_commande', [], Response::HTTP_SEE_OTHER);
    }

    /**
     * @Route("/front/{idC}", name="app_commande_delete_front", methods={"POST"})
     */
    public function deletefront(Request $request, Commande $commande, CommandeRepository $commandeRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $commande->getIdC(), $request->request->get('_token'))) {
            $commandeRepository->remove($commande, true);
        }

        return $this->redirectToRoute('mesCommande', [], Response::HTTP_SEE_OTHER);
    }


}
