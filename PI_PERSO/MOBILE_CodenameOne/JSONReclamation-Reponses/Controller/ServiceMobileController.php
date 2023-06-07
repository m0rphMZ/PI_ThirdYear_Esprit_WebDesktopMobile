<?php

namespace App\Controller;

use App\Entity\User;
use Symfony\Component\Mime\Email;
use App\Repository\UserRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

class ServiceMobileController extends AbstractController
{
    /*
    #[Route('/service/mobile', name: 'app_service_mobile')]
    public function index(): Response
    {
        return $this->render('service_mobile/index.html.twig', [
            'controller_name' => 'ServiceMobileController',
        ]);
    }

*/




    #[Route('/ListUser', name: 'ListUser', methods: ['GET'])]
    public function affichage_user(Request $request,UserRepository $userRepository,NormalizerInterface $normalizer)
    {
$users= $userRepository->findAll();
$usersNormalieses =  $normalizer->normalize($users,'json');
$json=json_encode($usersNormalieses);
return New Response($json);
    
    }





    #[Route('/UserJSON/{id}', name: 'show', methods: ['GET'])]
    public function show($id,UserRepository $userRepository,NormalizerInterface $normalizer)
    {
$users= $userRepository->find($id);
$usersNormalieses =  $normalizer->normalize($users,'json');
$json=json_encode($usersNormalieses);
return New Response($json);
    
    }



    #[Route('/newUserJson', name: 'new')]
    public function newUserJson(Request $request, NormalizerInterface $normalizer,MailerInterface $mailer)
    {

        /* http://127.0.0.1:8000/newUserJson?nom=json&prenom=json&email=json@gmail.com&role=Artiste&tel=58229725&image=profile_pic.jpg&mdp=azerty*/
        $user = new User();
     

       $user->setNom($request->get('nom'));
       $user->setPrenom($request->get('prenom'));
       $user->setEmail($request->get('email'));
       $user->setRole($request->get('role'));
       $user->setTel($request->get('tel'));
       /*$user->setImage($request->get('image')); */
       $user->setMdp($request->get('mdp'));
       $user->setEtat("activé");

       $entityManager = $this->getDoctrine()->getManager();
       $entityManager->persist($user);
        $entityManager->flush();
        $email = (new Email())
            ->from('touskieart.reclamations@gmail.com')
            ->to($user->getEmail())
            ->subject('L\'inscription est réussie')
            ->html('<p>Bonjour,' .$user->getNom(). ',</p><p> votre inscription a été complétée avec succès sur Touskieart App <p>Touskieart team</p>');

        
        $mailer->send($email);





       $jsonContent = $normalizer->normalize($user, 'json');
       return new Response(json_encode($jsonContent));

    }




    
    #[Route('/UpdateUserJSON/{id}', name: 'UpdateUserJSON')]
    public function UpdateUserJSON($id,Request $request,NormalizerInterface $normalizer)
    {

/* http://127.0.0.1:8000/UpdateUserJSON/61?nom=json&prenom=json&email=json111111@gmail.com&role=Artiste&tel=58229725&image=profile_pic.jpg&mdp=azerty */        

$em= $this->getDoctrine()->getManager();
$user=$em->getRepository(User::class)->find($id);


$user->setNom($request->get('nom'));
$user->setPrenom($request->get('prenom'));
$user->setEmail($request->get('email'));
$user->setRole($request->get('role'));
$user->setTel($request->get('tel'));
/*$user->setImage($request->get('image')); */
$user->setMdp($request->get('mdp'));
$user->setEtat("activé");
$em->flush();
$jsonContent = $normalizer->normalize($user, 'json');
return new Response("Mise a jour avec succées   ".json_encode($jsonContent));
    
    }



   

    #[Route('/DeleteUserJSON/{id}', name: 'DeleteUserJSON')]
    public function DeleteUserJSON($id,Request $request,NormalizerInterface $normalizer)
    {



$em= $this->getDoctrine()->getManager();
$user=$em->getRepository(User::class)->find($id);

$em->remove($user);
$em->flush();
$jsonContent = $normalizer->normalize($user, 'json');
return new Response("Suppression avec succées   ".json_encode($jsonContent));
    
    }


    

    






}
