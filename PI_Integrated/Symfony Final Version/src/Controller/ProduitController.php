<?php

namespace App\Controller;

use App\Entity\Produit;
use App\Entity\Redection;
use App\Entity\Categorie;
use App\Repository\ProduitRepository;
use Knp\Snappy\Pdf;
use App\Form\ProduitType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Knp\Component\Pager\PaginatorInterface; 
use Symfony\Component\Serializer\SerializerInterface;
use TCPDF;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use Swift_Message;
use Swift_Mailer;
use Swift_SmtpTransport;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Mailer\Transport\Smtp\SmtpTransport;
use Symfony\Component\Mailer\Transport\Smtp\Auth\LoginPasswordAuthenticator;






#[Route('/produit')]
class ProduitController extends AbstractController
{
    #[Route('/', name: 'app_produit_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager ,Request $request,ProduitRepository $prodf): Response
    {
        $produits = $entityManager
  
            ->getRepository(Produit::class)
            ->findAll();


            $entityManager = $this->getDoctrine()->getManager();

$queryBuilder = $entityManager->createQueryBuilder();
$queryBuilder->select('u.idcat')
    ->from(Produit::class, 'u');

$results1 = $queryBuilder->getQuery()->getResult();
//dd($results1);
            
            // chercher les Categorie 
            $categorie = $entityManager
            ->getRepository(Categorie::class)
            ->findAll();
          //  $categorieT = $produits->getIdcat();
          // dd($results1);
           // $filters = $request->get($categorie);
       
         $produit = $prodf->search($produits);

            if($request ->get('ajax'))
            {return new JsonResponse([
                'content'  => $this->renderView('produit/_fProduit.html.twig', compact('produit','categorie'))
                    
            ]);

            }

        return $this->render('produit/index.html.twig', [
            'produits' => $produits,'categorie'=> $categorie
        ]);
    }

    #[Route('/pdf', name: 'pdf', methods: ['GET', 'POST'])]
public function generatePdfAction(EntityManagerInterface $entityManager)
{
    $produits = $entityManager
  
            ->getRepository(Produit::class)
            ->findAll();
          

// créer une instance de TCPDF
$pdf = new TCPDF(PDF_PAGE_ORIENTATION, PDF_UNIT, PDF_PAGE_FORMAT, true, 'UTF-8', false);

// définir les informations du document
$pdf->SetCreator('Mon application Symfony');
$pdf->SetAuthor('Moi');
$pdf->SetTitle('Liste des clubs');
$pdf->SetSubject('Liste des clubs');

// ajouter une page
$pdf->AddPage();

// créer le contenu du PDF
$html = $this->renderView('produit/tab.html.twig', [
    'produits' => $produits,

]);

// écrire le contenu dans le PDF
$pdf->writeHTML($html, true, false, true, false, '');

// sauvegarder le PDF sur le bureau
     $projectDir = $this->getParameter('kernel.project_dir');

 // Use the project directory path to define the path to the PDF file
 $pdfPath = $projectDir . '/public/pdfs/liste_produit.pdf';
$pdf->Output($pdfPath, 'F');

// renvoyer une réponse HTTP
$response = new Response();
$disposition = $response->headers->makeDisposition(
    ResponseHeaderBag::DISPOSITION_ATTACHMENT,
    'liste_produit.pdf'
);
$response->headers->set('Content-Type', 'application/pdf');
$response->headers->set('Content-Disposition', $disposition);
$response->setContent(file_get_contents($pdfPath));

return $response;
}


    #[Route('/search', name: 'search', methods: ['GET'])]
    public function search(Request $request, ProduitRepository $cr, SerializerInterface $serializer): JsonResponse
    {
        $query = $request->query->get('name');
    
        // Effectuer la recherche dans votre base de données ou ailleurs
        $resultss = $cr->recherchee($query);
    
        // Retourner les résultats au format JSON
        $json = $serializer->serialize($resultss, 'json', ['groups' => 'produit']);
    
        return new JsonResponse([
            'resultss' => $resultss,
        ]);
    }


    #[Route('/new', name: 'app_produit_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $produit = new Produit();
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            
            // Handle file upload
            $file = $produit->getImageFile();
            if ($file) {
               
                $newFileName =uniqid().'.'.$file->guessExtension();
               
                $file->move(
                    $this->getParameter('images_directory'),
                    $newFileName
                );

                $produit->setImage($newFileName);
            }
            
            $entityManager->persist($produit);
            $entityManager->flush();

            return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('produit/new.html.twig', [
            'produit' => $produit,
            'form' => $form,
        ]);
    }
    #[Route('/Pfront', name: 'app_produit_indexF', methods: ['GET'])]
    public function indexF(EntityManagerInterface $entityManager, PaginatorInterface $paginator,Request $request): Response
    {
        $donnees = $entityManager
        ->getRepository(Produit::class)
        ->findAll();
        $articles = $paginator->paginate(
            $donnees, // Requête contenant les données à paginer (ici nos articles)
            $request->query->getInt('page', 1), // Numéro de la page en cours, passé dans l'URL, 1 si aucune page
            3 // Nombre de résultats par page
        );
        
        
            foreach ($donnees as $produit) {
               
                    if($produit->getQtestock() == 0)
                    {
                        $transport = (new Swift_SmtpTransport('smtp.gmail.com', 587, 'tls'))
                        ->setUsername('recycle.tunisia')
                        ->setPassword('ztntffukvpwraygm');
                    
                         $mailer = new Swift_Mailer($transport);
                         
                          $message = (new Swift_Message('produit epuisé'))
                        ->setFrom(['recycle.tunisia@gmail.com' => 'Admin'])
                        ->setTo('boulares.montassar@esprit.tn')
                        ->setBody('la quantite du produit '.$produit->getCodeproduit().'et a 0');
                    
                           $mailer->send($message); 
                    }
                
                
               
            

        }

        return $this->render('produit/front.html.twig', [
            'produits' => $articles,
        ]);
    }

    #[Route('/detail/{id}', name: 'app_produit_detail', methods: ['GET'])]
    public function showD(Produit $produit): Response
    {
        return $this->render('produit/detailP.html.twig', [
            'produit' => $produit,
        ]);
    }



    #[Route('/recherche', name: 'app_produit_search', methods: ['GET'])]
    public function indexsearch(EntityManagerInterface $entityManager, PaginatorInterface $paginator,Request $request,ProduitRepository $prodf): Response
    {
        $name = $request->get('name');
        $donnees =  $prodf->recherchee($name);
        $articles = $paginator->paginate(
            $donnees, // Requête contenant les données à paginer (ici nos articles)
            $request->query->getInt('page', 1), // Numéro de la page en cours, passé dans l'URL, 1 si aucune page
            3 // Nombre de résultats par page
        );
        $categorie = $entityManager->getRepository(Categorie::class)->findAll();
        return $this->render('produit/allProduit.html.twig', [
            'produits' => $articles,'categories' => $categorie
        ]);
    }
    #[Route('/AllProduit', name: 'app_produit_indexALL', methods: ['GET'])]
    public function indexFa(EntityManagerInterface $entityManager, PaginatorInterface $paginator,Request $request): Response
    {
        $donnees = $entityManager
        ->getRepository(Produit::class)
        ->findAll();
        $articles = $paginator->paginate(
            $donnees, // Requête contenant les données à paginer (ici nos articles)
            $request->query->getInt('page', 1), // Numéro de la page en cours, passé dans l'URL, 1 si aucune page
            3 // Nombre de résultats par page
        );
        
        $categorie = $entityManager->getRepository(Categorie::class)->findAll();
        return $this->render('produit/allProduit.html.twig', [
            'produits' => $articles,'categories' => $categorie
        ]);
    }
    #[Route('/trie', name: 'app_produit_trie', methods: ['GET'])]
    public function trie(EntityManagerInterface $entityManager, PaginatorInterface $paginator,Request $request,ProduitRepository $prodf): Response
    {
       
        $donnees = $prodf->trie();
        $articles = $paginator->paginate(
            $donnees, // Requête contenant les données à paginer (ici nos articles)
            $request->query->getInt('page', 1), // Numéro de la page en cours, passé dans l'URL, 1 si aucune page
            3 // Nombre de résultats par page
        );
        
        $categorie = $entityManager->getRepository(Categorie::class)->findAll();
        return $this->render('produit/allProduit.html.twig', [
            'produits' => $articles,'categories' => $categorie
        ]);
    }
    #[Route('/{id}', name: 'app_produit_show', methods: ['GET'])]
    public function show(Produit $produit): Response
    {
        return $this->render('produit/show.html.twig', [
            'produit' => $produit,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_produit_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Produit $produit, EntityManagerInterface $entityManager): Response
    {
      
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            
            // Handle file upload
            $file = $produit->getImageFile();
            if ($file) {
               
                $newFileName =uniqid().'.'.$file->guessExtension();
               
                $file->move(
                    $this->getParameter('images_directory'),
                    $newFileName
                );

                $produit->setImage($newFileName);
            }
            
            
            $entityManager->flush();
            return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('produit/edit.html.twig', [
            'produit' => $produit,
            'form' => $form,
        ]);
    }
    
    // #[Route('/{id}/edit', name: 'app_update1')]
    // public function update(Request $resquest,$id){

        
    //     $produit = $this->getDoctrine()->getRepository(produit::class)->find($id);
    //     $Form = $this->createForm(Classe::class,$produit);
    //     $Form->handleRequest($resquest);
    //     if ($Form->isSubmitted() && $Form->isValid()){
    //         $em = $this->getDoctrine()->getManager();
    //         // $file = $produit->getImageFile();
    //         //         if ($file) {
                       
    //         //             $newFileName =uniqid().'.'.$file->guessExtension();
                       
    //         //             $file->move(
    //         //                 $this->getParameter('images_directory'),
    //         //                 $newFileName
    //         //             );
        
    //         //             $produit->setImage($newFileName);
    //         //         }
                    
    //         $em ->persist($produit);
    //         $em->flush();
    //          $this->addFlash('notice','Updated successfully!');
    //          return $this->RedirectToRoute('app_produit_index');

    //     }
    
    //     return $this->render('produit/edit.html.twig',[
    //         'Form' => $Form->createView()
    //     ]);
    // } 

    #[Route('/{id}', name: 'app_produit_delete', methods: ['POST'])]
    public function delete(Request $request, Produit $produit, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$produit->getId(), $request->request->get('_token'))) {
            $entityManager->remove($produit);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
    }

    public function searchAction(Request $request, EntityManagerInterface $em)
{
    $query = $request->query->get('q');
    $results = $em->getRepository(produit::class)->search($query);
    return new JsonResponse($results);
}

#[Route('/detail1/{id}', name: 'app_produit_detail2', methods: ['GET'])]
    public function showDs(Request $request, Produit $produit ,EntityManagerInterface $entityManager ): Response
    { 
        $id = $request->get('q');

        $reduction = $entityManager->getRepository(Redection::class)->find($id);
        
        if($reduction->getValr())
        {
            $price = $reduction->getValr();
         $newprice =$price;
         $entityManager->remove($reduction);
            $entityManager->flush();
        }else {
            $newprice=0;
        }
        return $this->render('produit/detailP1.html.twig', [
            'produit' => $produit,'price'=>$newprice
        ]);
    }



}
