<?php

namespace App\Controller;

use App\Entity\User;
use Symfony\Component\Mime\Email;
use App\Repository\UserRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;


class PasswordForgetController extends AbstractController
{
    #[Route('/password/forget', name: 'app_password_forget')]
    public function index(): Response
    {
        return $this->render('password_forget/index.html.twig', [
            'controller_name' => 'PasswordForgetController',
        ]);
    }


    #[Route('/EmailVerif', name: 'EmailVerif')]
public function EmailVerif(Request $request, UserRepository $repo, SessionInterface $session,MailerInterface $mailer)
{



$formBuilder = $this->createFormBuilder();


$formBuilder->add('email', EmailType::class);





$form = $formBuilder->getForm();


$form->handleRequest($request);

if ($form->isSubmitted() && $form->isValid()) {
    
    $email = $form->getData()['email'];
    $user=$repo->findOneByEmail($email);

    if(!$user){


        $this->addFlash('error', 'Email incorrecte');
        return $this->redirectToRoute('EmailVerif');
    }


    else{


        $code = random_int(1000, 9999);
        

        $email = (new Email())
            ->from('touskieart.reclamations@gmail.com')
            ->to($user->getEmail())
            ->subject('code de verification')
            ->text('votre code de verification est ' . $code);
        
        $mailer->send($email);
        $session = $request->getSession();
        $session->set('user', $user);
        $session->set('code',$code);
        return $this->redirectToRoute('CodeVerif');


    }

    
}


return $this->render('user/EmailSet.html.twig', [
    'form' => $form->createView(),
]);
}







#[Route('/CodeVerif', name: 'CodeVerif')]
public function CodeVerif(Request $request, UserRepository $repo, SessionInterface $session)
{



$formBuilder = $this->createFormBuilder();


$formBuilder->add('code', NumberType::class, [
    'invalid_message' => 'le code doit être numérique.']);





$form = $formBuilder->getForm();


$form->handleRequest($request);

if ($form->isSubmitted() && $form->isValid()) {
    
    $code = $form->getData()['code'];
    

    if($code!=$session->get('code')){


        $this->addFlash('error', 'Code incorrecte');
        return $this->redirectToRoute('CodeVerif');
    }


    else{
    
        return $this->redirectToRoute('PassUpdate');

    }

    
}


return $this->render('user/code.html.twig', [
    'form' => $form->createView(),
]);
}






#[Route('/PassUpdate', name: 'PassUpdate')]
public function PassUpdate(Request $request, UserRepository $repo, SessionInterface $session)
{

    


$formBuilder = $this->createFormBuilder();


$formBuilder
    ->add('pass1', TextType::class, [
        'attr' => ['placeholder' => 'Mot de passe']
    ])
    ->add('pass2', TextType::class, [
        'attr' => ['placeholder' => 'Confirmation mot de passe']
    ]);

    $entityManager = $this->getDoctrine()->getManager();
    $user=$session->get('user');
    $user = $this->getDoctrine()->getRepository(User::class)->find($user->getIdUser());

$form = $formBuilder->getForm();


$form->handleRequest($request);

if ($form->isSubmitted() && $form->isValid()) {
    
    $pass1 = $form->getData()['pass1'];
    $pass2 = $form->getData()['pass2'];

    if($pass1!=$pass2){


        $this->addFlash('error', 'Les 2 mots de passe doivent être identiques');
        return $this->redirectToRoute('PassUpdate');
    }


    else {
        

$user->setMdp($pass1);
/*$repo->save($user,true);*/
$entityManager->flush();

$session->set('user', $user);

return $this->redirectToRoute('app_home');



    }


   

    
}


return $this->render('user/PassUpdate.html.twig', [
    'form' => $form->createView(),
]);
}















}
