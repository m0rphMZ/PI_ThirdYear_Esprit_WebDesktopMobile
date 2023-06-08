<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\UserType;
use App\Form\LoginFormType;
use App\Service\MailerService;
use Symfony\Component\Mime\Email;
use App\Repository\UserRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;

class LoginController extends AbstractController
{
    #[Route('/login', name: 'app_login')]
    public function login(Request $request,UserRepository $repo): Response
    {
        $form = $this->createForm(LoginFormType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            
            $data = $form->getData();
            $user = $repo->findOneBy(['email' => $data['email']]);
            

            if (!$user || $user->getMdp() !== $data['Password']) {
                
                $this->addFlash('error', 'Email ou mot de passe incorrect.');
                return $this->redirectToRoute('app_login');
            }
if ($user->getEtat()=="désactivé"){

    $this->addFlash('error', 'compte désactivé');
    return $this->redirectToRoute('app_login');

}


            $session = $request->getSession();
            $session->set('user', $user);
            if($user->getRole()=="Admin"){return $this->redirectToRoute('app_user_index');}
            else {return $this->redirectToRoute('app_home');}


            
        }

        return $this->render('user/login.html.twig', [
            'form' => $form->createView(),
        ]);
    }


    

    #[Route('/logout', name: 'app_logout')]
    public function logout(Request $request,UserRepository $repo): Response
    {

        $request->getSession()->remove('user');

        return $this->redirectToRoute('app_login');

    }





   
    #[Route('/register', name: 'app_user_register', methods: ['GET', 'POST'])]
    public function new(Request $request, UserRepository $userRepository , SluggerInterface $slugger): Response
    {
        $user = new User();
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
    
         
    
            $photo = $form->get('image')->getData();
    
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
                $user->setImage($newFilename);
            }
    
            
            $userRepository->save($user, true);
            $session = $request->getSession();
            $session->set('user', $user);


            

            

       

    
            return $this->redirectToRoute('app_home');
        }
    
        return $this->renderForm('user/register.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
    }
    
}



            
        








          
        



        
    
     