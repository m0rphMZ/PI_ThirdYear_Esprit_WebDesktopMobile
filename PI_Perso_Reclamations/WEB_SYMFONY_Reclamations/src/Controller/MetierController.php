<?php

namespace App\Controller;


use App\Form\EmailSetType;
use App\Form\LoginFormType;
use App\Repository\UserRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

class MetierController extends AbstractController
{
  

    #[Route('/metier', name: 'app_rech')]
    public function recherche(Request $request,UserRepository $repo,SessionInterface $session,PaginatorInterface $paginator): Response
    {
        $searchQuery = $request->query->get('search');
    
        $users = $repo->findBySearchQuery($searchQuery);
        $session->set('usr_search', $users);

        $pagination = $paginator->paginate(
            $users,
            $request->query->getInt('page', 1),
            4
        );
    
        return $this->render('user/index.html.twig', [
            'users' => $pagination,
            'searchQuery' => $searchQuery,
        ]);
    }


    #[Route('/filtre', name: 'app_rech_art')]
    public function index(Request $request, UserRepository $repo,SessionInterface $session,PaginatorInterface $paginator) : Response
    {
        $role = $request->query->get('role');
        
        if ($session->has('usr_search') && !empty($session->get('usr_search'))) {
            $usr_search = $session->get('usr_search');
            $users = [];
        
            foreach ($usr_search as $user) {
                $usersWithRole = $repo->findByRole($role); 
                foreach ($usersWithRole as $userWithRole) {
                    if ($user->getIdUser() == $userWithRole->getIdUser()) {
                        $users[] = $userWithRole;
                        break;
                    }
                }
            }
        } else {
            $users = $repo->findByRole($role);
        }
        
        $pagination = $paginator->paginate(
            $users,
            $request->query->getInt('page', 1),
            4
        );



        return $this->render('user/index.html.twig', [
            'users' =>  $pagination,
             'role' => $role,
            
        ]);
    }

    #[Route('/EmailSet', name: 'EmailSet')]
    public function EmailSet(Request $request,UserRepository $repo,SessionInterface $session){

        return $this->render('user/EmailSet.html.twig', [
            'controller_name' => 'HomeController',
        ]);
    
    }



    

}
