<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


use Symfony\Component\HttpFoundation\JsonResponse;

use App\Entity\Location;
use App\Entity\CategorieLoc;
use App\Repository\LocationRepository;
use App\Entity\User;
use Doctrine\ORM\EntityManagerInterface;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;



class JsonController extends AbstractController
{
    #[Route('/json', name: 'app_json')]
    public function index(): Response
    {
        return $this->render('json/index.html.twig', [
            'controller_name' => 'JsonController',
        ]);
    }


    


    #[Route('/afficherJsonLocal', name: 'jsonLocal')]
public function getEvenements(): JsonResponse
{
    $locations = $this->getDoctrine()->getRepository(Location::class)->findAll();
    
    $data = [];
    foreach ($locations as $location ) {
        $data[] = [
            'num_loc' => $location->getNumLoc(),
            'descipt_loc' => $location->getDesciptLoc(),
            'lieu_loc' => $location-> getLieuLoc(),
            'date_Aloc' => $location->getDateAloc()->format('Y-m-d'),
            'surface_loc' => $location->getSurfaceLoc(),
            
            'nb_pers_loc' => $location->getNbPersLoc(),
            'prix_loc' => $location->getPrixLoc(),
           'equipements' => $location->getEquipements(),
            'disponibilite' => $location->getDisponibilite(),
             //'image' => $location->getImage(),
            'code_catg' => $location->getCodeCatg()->getCodecLoc(),
           // 'user_id' => $location->getUser()->getIdUser()
        ];
    }
    
    return new JsonResponse($data);
}


#[Route('/api/ajoutLocal', name: 'local_json_create')]
    public function create(Request $request, EntityManagerInterface $entityManager,NormalizerInterface $normalizer): JsonResponse
    {


        $requestData = json_decode($request->getContent(), true);
        $em = $this->getDoctrine()->getManager() ;
        $local = new Location();
       
        $local->setDesciptLoc($request->get('descipt_loc'));
        $local->setLieuLoc($request->get('lieu_loc'));
        $local->setDateAloc(new \DateTime($request->get('date_Aloc')));
        $local->setSurfaceLoc($request->get('surface_loc'));
        $local->setPrixLoc($request->get('prix_loc'));
        $local->setEquipements($request->get('equipements'));
        $local->setDisponibilite($request->get('disponibilite'));
        $local->setNbPersLoc($request->get('nb_pers_loc'));
        $local->setImage($request->get('image'));

       // $proprietaire = $this->getDoctrine()
      //   ->getRepository(User::class)
      // ->find($request->request->get('idUser'));

        $code_catg = $this->getDoctrine()
            ->getRepository(CategorieLoc::class)
            ->find($request->get('code_catg'));
            $local->setCodeCatg($code_catg );
       
       
       
            $entityManager->persist($local);
        $entityManager->flush();
                return $this->json(
        [          
            'num_loc' => $local->getNumLoc(),
            'descipt_loc' => $local->getDesciptLoc(),
            'lieu_loc' => $local-> getLieuLoc(),
       'date_Aloc' => $local->getDateAloc()->format('Y-m-d'),
            'surface_loc' => $local->getSurfaceLoc(),
            
            'nb_pers_loc' => $local->getNbPersLoc(),
            'prix_loc' => $local->getPrixLoc(),
           'equipements' => $local->getEquipements(),
            'disponibilite' => $local->getDisponibilite(),
             'image' => $local->getImage(),
           'code_catg' => $local->getCodeCatg()->getCodecLoc(),
          
        ]);
    }

  
    #[Route('/UpdateLocalJSON/{id}', name: 'UpdateLocalJSON')]
    public function UpdateUserJSON($id,Request $request,NormalizerInterface $normalizer)
    {

    /* http://127.0.0.1:8000/UpdateUserJSnom=json&prenom=json&email=json1ON/61?11111@gmail.com&role=Artiste&tel=58229725&image=profile_pic.jpg&mdp=azerty */        

$em= $this->getDoctrine()->getManager();
$local=$em->getRepository(Location::class)->find($id);
$local->setLieuLoc($request->get('lieu_loc'));
$local->getDesciptLoc($request->get('descipt_loc'));

$local->setSurfaceLoc($request->get('surface_loc'));
$local->setNbPersLoc($request->get('nb_pers_loc'));
$local->setPrixLoc($request->get('prix_loc'));
$local->setEquipements($request->get('ee'));
$local->setDisponibilite($request->get('disponibilite'));
  //$local->setDateAloc($local->getDateAloc);
   //$local->setCodeCatg($local->setCodeCatg()->getCodecLoc());
   //$user->setImageLoc($local->getImageLoc); 
  //$user->setMdp($request->get('mdp'));
//$user->setEtat("activé");
 $em->flush();
 $jsonContent = $normalizer->normalize($local, 'json');
 return new Response("Mise a jour avec succées   ".json_encode($jsonContent));
    
    }



















    #[Route('/api/delete/local/{id}', name: 'local_json_delete')]
    public function delete($id): JsonResponse
    {   $em = $this->getDoctrine()->getManager() ;
        $local = $em->getRepository(Location::class)->find($id) ;
        $em->remove($local);
        $em->flush();

        return $this->json(['message' => 'Le local a été supprimée avec succès.']);
    }





    

    



}
