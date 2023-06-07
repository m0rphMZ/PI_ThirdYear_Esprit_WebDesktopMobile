<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\UserType;
use App\Repository\UserRepository;
use PhpOffice\PhpSpreadsheet\Spreadsheet;
use PhpOffice\PhpSpreadsheet\Writer\Xlsx;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;

#[Route('/user')]
class UserController extends AbstractController
{
    #[Route('/', name: 'app_user_index', methods: ['GET'])]
    public function index(Request $request,UserRepository $userRepository,PaginatorInterface $paginator): Response
    {
$users= $userRepository->findAll();

$pagination = $paginator->paginate(
    $users,
    $request->query->getInt('page', 1),
    4
);


        return $this->render('user/index.html.twig', [
            'users' => $pagination,
        ]);
    }

    #[Route('/new', name: 'app_user_new', methods: ['GET', 'POST'])]
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

            return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('user/new.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
    }




    

    #[Route('/{idUser}', name: 'app_user_show', methods: ['GET'])]
    public function show(User $user, Request $request): Response
    {
        $session = $request->getSession();
        $user_connected = $session->get('user');
        if ($user_connected->getRole()=="Admin"){
            return $this->render('user/showAdmin.html.twig', [
                'user' => $user,
            ]);
        }
        
        else {
        return $this->render('user/show.html.twig', [
            'user' => $user,
        ]);}
    }








    #[Route('/{idUser}/edit', name: 'app_user_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, User $user, UserRepository $userRepository,SluggerInterface $slugger): Response
    {
        $session = $request->getSession();
        $user_connected = $session->get('user');
        $form = $this->createForm(UserType::class, $user);

        if($user_connected->getRole()=='Admin'){

           $form->add('etat', ChoiceType::class, [
            'choices' => [
                'Activé' => 'activé',
                'Désactivé' => 'désactivé',
                
            ],
            'expanded' => true,
            'multiple' => false,
            
        ]) ;
        }
    
    $form->remove('mdp');
    
    $form->add('mdp', TextType::class);

    $form->remove('image');

    
        $form->add('image', FileType::class, [
            'label' => 'image',
            'mapped' => false,
            'required' => false,
            'constraints' => [
                new File([
                    'maxSize' => '2M',
                    'mimeTypes' => [
                        'image/jpeg',
                        'image/png',
                        'image/gif',
                    ],
                    'mimeTypesMessage' => 'Veuillez sélectionner une image valide (jpeg, png, gif)',
                    'maxSizeMessage' => 'La taille de l\'image ne doit pas dépasser 2 Mo.',
                ]),
            ],
        ]);
   


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
         
            

            if($user_connected->getRole()=='Admin'){
                return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);}
        
                else {
                    
                    
                    
                    return $this->redirectToRoute('app_user_show', ['idUser' => $user_connected->getIdUser()]);}
        }
     
        return $this->renderForm('user/edit.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
    }

    #[Route('/{idUser}', name: 'app_user_delete', methods: ['POST'])]
    public function delete(Request $request, User $user, UserRepository $userRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$user->getIdUser(), $request->request->get('_token'))) {
            $userRepository->remove($user, true);
        }

        $session = $request->getSession();
        $user_connected = $session->get('user');

        if($user_connected->getRole()=='Admin'){
        return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);}

        else {return $this->redirectToRoute('app_logout');}
    }




    




   
   


}