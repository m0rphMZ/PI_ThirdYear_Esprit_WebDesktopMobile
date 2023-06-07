<?php

namespace App\Controller;

use App\Entity\Location;
use App\Entity\CategorieLoc;
use App\Form\LocationType;
use App\Repository\LocationRepository;
use App\Repository\CategorieLocRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;

use Yectep\PhpSpreadsheetBundle\YectepPhpSpreadsheetBundle;
use Yectep\PhpSpreadsheetBundle\Factory;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use Vich\UploaderBundle\Handler\UploadHandler;


use Doctrine\ORM\EntityManagerInterface;
use App\Form\TriType;

use PhpOffice\PhpSpreadsheet\Writer\Xlsx;
//use Symfony\Component\Form\Extension\Core\Type\FileType;
//use Symfony\Component\Validator\Constraints\File;
use Knp\Component\Pager\PaginatorInterface;

use Symfony\Component\Notifier\ChatterInterface;
use Symfony\Component\Notifier\Message\ChatMessage;


use Symfony\Component\Notifier\Notification\Notification;
use Symfony\Component\Notifier\Bridge\Slack\Block\SlackDividerBlock;
use Symfony\Component\Notifier\Bridge\Slack\Block\SlackSectionBlock;
use Symfony\Component\Notifier\Bridge\Slack\SlackOptions;



use Symfony\Component\Notifier\Notifier;

use Symfony\Component\Notifier\NotifierInterface;
use Symfony\Component\Notifier\Recipient\Recipient;
use Symfony\Component\Notifier\Transport\TransportInterface;
use Symfony\Component\Notifier\Factory\SlackWebhookNotifierFactory;



#[Route('/location')]
class LocationController extends AbstractController
{

#[Route('/', name: 'app_location_index', methods: ['GET'])]
    public function index(Request $request, LocationRepository $locationRepository, PaginatorInterface $paginator): Response
    {
        $criteria = [
            'lieu_loc' => $request->query->get('lieuLoc'),
            'disponibilite' => $request->query->get('disponibilite'),
            'prix_loc' => $request->query->get('prixLoc'),
           
        ];
    
        $sortOptions = [
            'lieuLoc_asc' => 'e.lieu_loc',
            'disponibilite_asc' => 'e.disponibilite',
            'prixLoc_asc' => 'e.prix_loc',
        ];
    
        $sort = $request->query->get('sort');
        if (isset($sortOptions[$sort])) {
            $sortCriteria = $sortOptions[$sort];
        } else {
            $sortCriteria = 'e.lieu_loc'; // default sorting
        }
    
        $orderOptions = [
            'asc' => 'asc',
            'desc' => 'desc',
        ];
        $order = $request->query->get('order') ?? 'asc';
    
        if ($criteria) {
            $locations = $locationRepository->search($criteria, $sortCriteria, $order);
        } else {
            $locations = $locationRepository->findAll();
        }
    
        $pagination = $paginator->paginate(
            $locations,
            $request->query->getInt('page', 1),
            3
        );
    
        return $this->render('location/index.html.twig', [
            'locations' => $pagination,
            'criteria' => $criteria,
            'sort' => $sort,
        ]);
    }
    



  



    
       /* $locations=$locationRepository->findAll();
        $locations = $paginator->paginate(
            $locations, /* query NOT result */
          /*  $request->query->getInt('page', 1),
            2
        );
        return $this->render('location/index.html.twig', [
            'locations' => $locations,
        ]);*/

/*
        $rechercheString = $request->query->get('rechercheString');

        if ($rechercheString == null) {
            $locations = $locationRepository->findAll(); // Remplacez "searchByTitle" par la méthode que vous utilisez pour rechercher les cours

        } else {
            $locations = $locationRepository->recherche($rechercheString);
        }

        $pagination = $paginator->paginate(
            $locations, /* query NOT result */
           // $request->query->getInt('page', 1), /*page number*/
            //3 /*limit per page*/
      //  );

      //  return $this->render('location/index.html.twig', [
          //  'locations' => $pagination,
           // 'tri' => 'ASC'
       // ]);



   


    #[Route('/aa', name: 'app_location_indexx', methods: ['GET'])]
    public function indexx(Request $request, LocationRepository $locationRepository, PaginatorInterface $paginator): Response

    { $criteria = [
        'lieu_loc' => $request->query->get('lieuLoc'),
        'disponibilite' => $request->query->get('disponibilite'),
        'prix_loc' => $request->query->get('prixLoc'),
       
    ];

    $sortOptions = [
        'lieuLoc_asc' => 'e.lieu_loc',
        'disponibilite' => 'e.disponibilite',
        'prixLoc_asc' => 'e.prix_loc',
    ];

    $sort = $request->query->get('sort');
    if (isset($sortOptions[$sort])) {
        $sortCriteria = $sortOptions[$sort];
    } else {
        $sortCriteria = 'e.lieu_loc'; // default sorting
    }

    $orderOptions = [
        'asc' => 'asc',
        'desc' => 'desc',
    ];
    $order = $request->query->get('order') ?? 'asc';

    if ($criteria) {
        $locations = $locationRepository->search($criteria, $sortCriteria, $order);
    } else {
        $locations = $locationRepository->findAll();
    }

    $pagination = $paginator->paginate(
        $locations,
        $request->query->getInt('page', 1),
        3
    );

    return $this->render('location/Front.html.twig', [
        'locations' => $pagination,
        'criteria' => $criteria,
        'sort' => $sort,
    ]);












        return $this->render('location/Front.html.twig', [
            'locations' => $locationRepository->findAll(),
        ]);
    }
    #[Route('/new', name: 'app_location_new', methods: ['GET', 'POST'])]
    public function new(Request $request, LocationRepository $locationRepository, SluggerInterface $slugger, UploadHandler $uploadHandler): Response
    {
        $location = new Location();
        $form = $this->createForm(LocationType::class, $location);
        
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {


        /*    $photo = $form->get('image')->getData();

            
            if ($photo) {
                $originalFilename = pathinfo($photo->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$photo->guessExtension();

                // Move the file to the directory where brochures are stored
                try {
                    $photo->move(
                        $this->getParameter('user_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // updates the 'brochureFilename' property to store the PDF file name
                // instead of its contents
                $location->setImage($newFilename);
      }
*/ if ($location->getImageFile() instanceof UploadedFile) {
    $imageFile = $location->getImageFile();
    $uploadHandler->upload($location, $imageFile);
    $location->setImage($imageFile->getClientOriginalName());
}


            $locationRepository->save($location, true);

            return $this->redirectToRoute('app_location_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('location/new.html.twig', [
            'location' => $location,
            'form' => $form,
        ]);
    }

    #[Route('/{numLoc}', name: 'app_location_show', methods: ['GET'])]
    public function show(Location $location): Response
    {
        return $this->render('location/show.html.twig', [
            'location' => $location,
        ]);
    }

    #[Route('/{numLoc}/edit', name: 'app_location_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Location $location, LocationRepository $locationRepository,SluggerInterface $slugger, UploadHandler $uploadHandler): Response
    {
        $form = $this->createForm(LocationType::class, $location);

        
   
        $form->handleRequest($request);

       
          /*  if ($form->isSubmitted() && $form->isValid()) {
            //Get image path
                $image = $form->get('image')->getData();
                if ($image) {
                    $originalFilename = pathinfo($image->getClientOriginalName(), PATHINFO_FILENAME);
                    // this is needed to safely include the file name as part of the URL
                    $safeFilename = $slugger->slug($originalFilename);
                    $newFilename = $safeFilename.'-'.uniqid().'.'.$image->guessExtension();
    
                    // Move the file to the directory where user uploaded images are stored
                    try {
                        $image->move(
                            $this->getParameter('user_directory'),
                            $newFilename
                        );
                    } catch (FileException $e) {
                        // ... handle exception if something happens during file upload
                    }
    
                    // sets the image name as affiche
                    $location->setImage($newFilename);
                }*/



                if ($form->isSubmitted() && $form->isValid()) {
                    // Handle image upload
                    if ($location->getImageFile() instanceof UploadedFile) {
                        $imageFile = $location->getImageFile();
                        $uploadHandler->upload($location, $imageFile);
                        $location->setImage($imageFile->getClientOriginalName());
                    }
        


            $locationRepository->save($location, true);

            return $this->redirectToRoute('app_location_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('location/edit.html.twig', [
            'location' => $location,
            'form' => $form,
        ]);
    }

    #[Route('/{numLoc}', name: 'app_location_delete', methods: ['POST'])]
    public function delete(Request $request, Location $location, LocationRepository $locationRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$location->getNumLoc(), $request->request->get('_token'))) {
            $locationRepository->remove($location, true);
        }

        return $this->redirectToRoute('app_location_index', [], Response::HTTP_SEE_OTHER);
    }





    
    #[Route('/location/excel', name: 'app_location_excel', methods: ['GET', 'POST'])]
    public function showExcel(Factory $spreadsheetFactory):Response
    {
      /*
        $spreadsheet = new Spreadsheet();

        $spreadsheet = new Spreadsheet();

        // Ajouter des données à la feuille active
        $sheet = $spreadsheet->getActiveSheet();
        $sheet->setCellValue('A1', 'Hello');
        $sheet->setCellValue('B1', 'World!');
    
        // Enregistrer le classeur dans un fichier Excel
        $writer = new Xlsx($spreadsheet);
        $writer->save('hello_world.xlsx');
    
        // Return a response object with a success message
        return new Response('Spreadsheet created successfully!');*/






       // Créer un nouveau classeur
  /*
// Create a new Spreadsheet object
$spreadsheet = new Spreadsheet();

// Add some data to the spreadsheet
$sheet = $spreadsheet->getActiveSheet();
$sheet->setCellValue('A1', 'Hello');
$sheet->setCellValue('B1', 'World!');

// Save the spreadsheet to a file
$writer = new Xlsx($spreadsheet);
$writer->save('hey.xlsx');

// Load the spreadsheet from the file
$spreadsheet = IOFactory::load('hey.xlsx');
return new Response('Spreadsheet created successfully!');
    }


*/

    // Récupérer les données de la base de données
    $entityManager = $this->getDoctrine()->getManager();
    $data = $entityManager->getRepository(Location::class)->findAll();

    // Créer un nouveau fichier Excel
    $spreadsheet = $spreadsheetFactory->createSpreadsheet();

    // Ajouter les en-têtes de colonne
    $sheet = $spreadsheet->getActiveSheet();
    $sheet->setCellValue('A1', 'ID');
    $sheet->setCellValue('B1', 'Nom');
    $sheet->setCellValue('C1', 'Adresse');
    $sheet->setCellValue('D1', 'Téléphone');

    // Ajouter les données
    $row = 2;
    foreach ($data as $item) {
        $sheet->setCellValue('A'.$row, $item->getNumLoc());
        $sheet->setCellValue('B'.$row, $item->getDesciptLoc());
        $sheet->setCellValue('C'.$row, $item->getLieuLoc());
        $sheet->setCellValue('D'.$row, $item->getSurfaceLoc());
        $row++;
    }

    // Créer un fichier temporaire pour le téléchargement
    $fileName = 'export.xlsx';
    $tempFilePath = tempnam(sys_get_temp_dir(), $fileName);

    // Enregistrer le fichier Excel dans le fichier temporaire
    $writer = $spreadsheetFactory->createWriter($spreadsheet, 'Xlsx');
    $writer->save($tempFilePath);

    // Configurer la réponse HTTP pour le téléchargement du fichier
    $response = new BinaryFileResponse($tempFilePath);
    $response->setContentDisposition(ResponseHeaderBag::DISPOSITION_ATTACHMENT, $fileName);

    // Supprimer le fichier temporaire après l'envoi de la réponse
    $response->deleteFileAfterSend(true);

    return $response;

    }




    #[Route('/location/statistic', name: 'app_location_statistic')]
    public function statistic(): Response
    {
        $categories = $this->getDoctrine()->getRepository(CategorieLoc::class)->findAll();
        $categColor = [];

        $data = [];
        foreach ($categories as $category) {
            $locations = $this->getDoctrine()->getRepository(Location::class)->findBy(['codeCatg' => $category]);
            $categColor[] = $category->getColor();
            $data[$category->getLibellecLoc()] = count($locations);
        }

        
        return $this->render('location/statistic.html.twig',['data'=> $data,'categColor' => json_encode($categColor)]);
    }




    





// ...



#[Route('/ccheckout/thankyou', name: 'thankyou')]
public function sendSlackMessage(ChatterInterface $chatter,LocationRepository $locationRepository,Request $request,  PaginatorInterface $paginator): Response
{

    $message = (new ChatMessage('je suis interesee par le local'))
            // if not set explicitly, the message is sent to the
            // default transport (the first one configured)
          
            ->transport('slack');

        $sentMessage = $chatter->send($message);
        




        $criteria = [
            'lieu_loc' => $request->query->get('lieuLoc'),
            'surface_loc' => $request->query->get('surfaceLoc'),
            'prix_loc' => $request->query->get('prixLoc'),
           
        ];
    
        $sortOptions = [
            'lieuLoc_asc' => 'e.lieu_loc',
            'surfaceLoc_asc' => 'e.surface_loc',
            'prixLoc_asc' => 'e.prix_loc',
        ];
    
        $sort = $request->query->get('sort');
        if (isset($sortOptions[$sort])) {
            $sortCriteria = $sortOptions[$sort];
        } else {
            $sortCriteria = 'e.lieu_loc'; // default sorting
        }
    
        $orderOptions = [
            'asc' => 'asc',
            'desc' => 'desc',
        ];
        $order = $request->query->get('order') ?? 'asc';
    
        if ($criteria) {
            $locations = $locationRepository->search($criteria, $sortCriteria, $order);
        } else {
            $locations = $locationRepository->findAll();
        }
    
        $pagination = $paginator->paginate(
            $locations,
            $request->query->getInt('page', 1),
            3
        );

        return $this->render('location/Front.html.twig', [
            'locations' => $pagination,
            'criteria' => $criteria,
            'sort' => $sort,
        ]); 

   // ...



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














    #[Route('/neww', name: 'app_rendez_vous_new', methods: ['GET', 'POST'])]
    public function neweeew(Request $request,QR_code_generator_api $qr_code_generator_api,MailerInterface $mailer, ManagerRegistry $doctrine,LocationRepository $locationRepository, AnnonceRepository $annonceRepository,CandidatureRepository $candidatureRepository): Response
    {
        
        $locations = $locationRepository->findAll(); 
            
            $email = (new Email())
                ->from('validation.message@gmail.com')
                ->to('aziz.gadacha@esprit.tn')
                //->cc('cc@example.com')
                //->bcc('bcc@example.com')
                //->replyTo('fabien@example.com')
                //->priority(Email::PRIORITY_HIGH)
                ->subject('Time for Symfony Mailer!')
                ->text('Sending emails is fun again!')

->html();
            $this->mailer->send($email);
          

        return $this->renderForm('location/index.html.twig', ['locations'=>$locations

            
]);
    }



// src/Controller/LocationController.php

    

}

    


