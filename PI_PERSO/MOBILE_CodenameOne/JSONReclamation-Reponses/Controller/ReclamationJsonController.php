<?php

namespace App\Controller;

use App\Entity\User;
use App\Entity\Reponses;
use App\Entity\Reclamation;
use Symfony\Component\Mime\Email;
use App\Repository\ReponsesRepository;
use App\Repository\ReclamationRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;



class ReclamationJsonController extends AbstractController
{
   

    // // _________________________________________________________________________________________________________________

    //                                         JSON

    //  // _________________________________________________________________________________________________________________ 


    #[Route('reclamationJSON/new', name: 'new_reclamationJSON')]
    public function newRecJson(Request $request, NormalizerInterface $Normalizer)
    {
        $reclamation = new Reclamation();
        $reclamation->setTitreRec($request->get('titreRec'));
        $reclamation->setType($request->get('typeRec'));
        $reclamation->setDescription($request->get('descRec'));
        $reclamation->setDateCreation(new \DateTime());

        $entityManager = $this->getDoctrine()->getManager();

        $userId = $request->get('userId');
        $user = $entityManager->getRepository(User::class)->find($userId);
        if (!$user) {
            return new Response('User not found', Response::HTTP_NOT_FOUND);
        }

        $reclamation->setUser($user);
                
        $entityManager->persist($reclamation);
        $entityManager->flush();
        $jsonContent = $Normalizer->normalize($reclamation, 'json', ['groups' => 'reclamations']);
        return new Response(json_encode($jsonContent));
    }




    #[Route('reclamationsParUserIdJSON/', name: 'rec_reclamationsParUserIdJSON')]
    public function getRecParUserId(Request $request, NormalizerInterface $Normalizer, ReclamationRepository $reclamationRepository)
    {
        $userId = $request->get('userId');
        $reclamations = $reclamationRepository->findByUserId($userId);
        $reclamationsNormalises = $Normalizer->normalize($reclamations, 'json');

        $json = json_encode($reclamationsNormalises);

        return new Response($json);

    }

    #[Route('reponsesParRecIdJSON/', name: 'rep_reponsesParRecIdJSON')]
    public function getRepsParRecId( Request $request, NormalizerInterface $Normalizer, ReponsesRepository $reponsesrepository)
    {
        $id = $request->query->get('recId');
        // $id = $request->attributes->get('id');

        
        $entityManager = $this->getDoctrine()->getManager();
    $reclamation = $entityManager->getRepository(Reclamation::class)->find($id);

    if (!$reclamation) {
        throw $this->createNotFoundException('Reclamation not found');
    }

    $reponses = $reponsesrepository->findResponsesByReclamation($reclamation);

        $reponsesNormalises = $Normalizer->normalize($reponses, 'json');

        $json = json_encode($reponsesNormalises);

        return new Response($json);

    }

    



    #[Route('reclamationParRecIdJSON/{recId}', name: 'rec_reclamationParRecIdJSON')]
        public function getRecParRecId($recId, Request $request, NormalizerInterface $Normalizer, ReclamationRepository $reclamationRepository)
        {
            $recId = $request->get('recId');
            $reclamation = $reclamationRepository->find($recId);
            $reclamationNormalises = $Normalizer->normalize($reclamation, 'json');

            $json = json_encode($reclamationNormalises);

            return new Response($json);
        }




        


        #[Route('reclamationEtReponsesParRecIdJSON/{recId}', name: 'rec_reclamationEtReponsesParRecIdJSON')]
        public function getRecEtRepsParRecId($recId, Request $request, NormalizerInterface $Normalizer, ReclamationRepository $reclamationRepository, ReponsesRepository $reponsesrepository)
        {
            $reclamation = $reclamationRepository->find($recId);
            if (!$reclamation) {
                throw $this->createNotFoundException('Reclamation not found');
            }

            $reclamationNormalises = $Normalizer->normalize($reclamation, 'json');

            $reponses = $reponsesrepository->findResponsesByReclamation($reclamation);
            $reponsesNormalises = $Normalizer->normalize($reponses, 'json');

            $result = array_merge($reclamationNormalises, ['reponses' => $reponsesNormalises]);
            $json = json_encode($result);

            return new Response($json);
}









        #[Route('reclamationNewRepJSON/', name: 'new_reponseJSON')]
        public function newRepJson(Request $request, NormalizerInterface $Normalizer, \Symfony\Component\Mailer\MailerInterface $mailer)
        {
            $recId = $request->query->get('recId');
            $userId = $request->query->get('userId');
            $adminResp = $request->query->get('adminResp');

            $entityManager = $this->getDoctrine()->getManager();
            $reclamation = $entityManager->getRepository(Reclamation::class)->find($recId);

            $reponse = new Reponses();
            
            $reponse->setIsadminreponse(false);
            $reponse->setRepDescription($request->get('repDesc'));
            $reponse->setDateRep(new \DateTime());
            $entityManager = $this->getDoctrine()->getManager();


            $user = $entityManager->getRepository(User::class)->find($userId);

            $reponse->setUser($user);
            $reponse->setRec($reclamation);
            $entityManager->persist($reponse);
            $entityManager->flush();

            $email = (new Email())
            ->from('touskieart.reclamations@gmail.com')
            ->to($reclamation->getUser()->getEmail())
            ->subject('Nouvelle réponse ajoutée à votre réclamation sur Touskieart App')
            ->html('<p>Bonjour ' . $reclamation->getUser()->getNom() . ',</p><p>Une nouvelle réponse a été ajoutée à votre réclamation "' . $reclamation->getTitreRec() . '" sur Touskieart app.</p><p>Veuillez ouvrir l\'application pour voir la réponse.</p><p>Merci,</p><p>Touskieart team</p>');
            $mailer->send($email);



            $jsonContent = $Normalizer->normalize($reponse, 'json', ['groups' => 'reponse']);
            return new Response(json_encode($jsonContent));
        }


        #[Route('deleteRecJSON/{recId}', name: 'deleteRecJSON')]
        public function deleteRecByRecId($recId, Request $request, NormalizerInterface $Normalizer)
        {
            $entityManager = $this->getDoctrine()->getManager();
            $reclamation = $entityManager->getRepository(Reclamation::class)->find($recId);

            $entityManager->remove($reclamation);
            $entityManager->flush();


            $jsonContent = $Normalizer->normalize($reclamation, 'json', ['groups' => 'reclamations']);

            return new Response("Reclamation deleted" . json_encode($jsonContent));
           
        }


// _______________________________________________________________________________

//         ADMIN SIDE 
// ___________________________________________________________________________



#[Route('reclamationsAdminJSON/', name: 'rec_adminJSON')]
    public function getAll( NormalizerInterface $Normalizer, ReclamationRepository $reclamationRepository)
    {

        $reclamations = $reclamationRepository->findAllReclamations();
        $reclamationsNormalises = $Normalizer->normalize($reclamations, 'json');

        $json = json_encode($reclamationsNormalises);

        return new Response($json);

    }




    #[Route('reclamationNewRepAdminJSON/', name: 'new_reponseAdminJSON')]
        public function newRepAdminJson(Request $request, NormalizerInterface $Normalizer, \Symfony\Component\Mailer\MailerInterface $mailer)
        {
            $recId = $request->query->get('recId');
            $userId = $request->query->get('userId');
            $adminResp = $request->query->get('adminResp');

            $entityManager = $this->getDoctrine()->getManager();
            $reclamation = $entityManager->getRepository(Reclamation::class)->find($recId);

            $reponse = new Reponses();
            
            $reponse->setIsadminreponse(true);
            $reponse->setRepDescription($request->get('repDesc'));
            $reponse->setDateRep(new \DateTime());
            $entityManager = $this->getDoctrine()->getManager();


            $user = $entityManager->getRepository(User::class)->find($userId);

            $reponse->setUser($user);
            $reponse->setRec($reclamation);
            $entityManager->persist($reponse);
            $entityManager->flush();

            $email = (new Email())
            ->from('touskieart.reclamations@gmail.com')
            ->to($reclamation->getUser()->getEmail())
            ->subject('Nouvelle réponse Administrateur ajoutée à votre réclamation sur Touskieart App')
            ->html('<p>Bonjour ' . $reclamation->getUser()->getNom() . ',</p><p>Une nouvelle réponse par un Administrateur a été ajoutée à votre réclamation "' . $reclamation->getTitreRec() . '" sur Touskieart app.</p><p>Veuillez ouvrir l\'application pour voir la réponse.</p><p>Merci,</p><p>Touskieart team</p>');
            $mailer->send($email);



            $jsonContent = $Normalizer->normalize($reponse, 'json', ['groups' => 'reponse']);
            return new Response(json_encode($jsonContent));
        }





        #[Route('adminClosereclamation/{recId}', name: 'close_reclamation')]

    public function closeReclamation($recId, Request $request,Reclamation $reclamation,NormalizerInterface $Normalizer): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
        $reclamation = $entityManager->getRepository(Reclamation::class)->find($recId);

        $reclamation->setStatus('Fermée');
        $reclamation->setDateFin(new \DateTime());
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();
        
        $jsonContent = $Normalizer->normalize($reclamation, 'json', ['groups' => 'reclamations']);

        return new Response("Reclamation closed" . json_encode($jsonContent));
    }


    









}