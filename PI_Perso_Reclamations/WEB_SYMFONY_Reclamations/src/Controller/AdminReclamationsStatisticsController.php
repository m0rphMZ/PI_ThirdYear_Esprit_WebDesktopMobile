<?php

namespace App\Controller;

use App\Repository\ReponsesRepository;
use App\Repository\ReclamationRepository;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\UX\Chartjs\Builder\ChartBuilderInterface;
use Symfony\UX\Chartjs\Model\Chart;
use App\Entity\User;
use App\Entity\Reponses;
use App\Entity\Reclamation;
use Symfony\Component\Mime\Email;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

class AdminReclamationsStatisticsController extends AbstractController
{
    #[Route('/admin/reclamations/statistics', name: 'app_admin_reclamations_statistics')]
public function index(ReclamationRepository $reclamationRepository, ReponsesRepository $reponsesRepository): Response
{
    // Fetch all reclamations
    $reclamations = $reclamationRepository->findAll();
    
    // Fetch all responses
    $responses = $reponsesRepository->findAll();
    
    // Calculate the total number of reclamations
    $totalReclamations = count($reclamations);
    
    // Calculate the total number of responses
    $totalResponses = count($responses);
    
    // Calculate the average number of responses per reclamation
    $averageResponsesPerReclamation = $totalReclamations > 0 ? round($totalResponses / $totalReclamations, 2) : 0;
    
    // Calculate the number of resolved reclamations
    $resolvedReclamations = $reclamationRepository->findBy(['status' => 'Fermée']);
    
    // Calculate the number of unresolved reclamations
    $unresolvedReclamations = $reclamationRepository->findBy(['status' => 'Ouvert']);
    
    // Calculate the percentage of resolved reclamations
    $percentageResolvedReclamations = $totalReclamations > 0 ? round(count($resolvedReclamations) / $totalReclamations * 100) : 0;
    
    // Calculate the percentage of unresolved reclamations
    $percentageUnresolvedReclamations = $totalReclamations > 0 ? round(count($unresolvedReclamations) / $totalReclamations * 100) : 0;
    
    // Get the number of reclamations per type
    $reclamationsByType = $reclamationRepository->createQueryBuilder('r')
        ->select('r.type, COUNT(r) AS count')
        ->groupBy('r.type')
        ->getQuery()
        ->getResult();
    
    // Get the type with the most reclamations
    $typeWithMostReclamations = null;
    $maxReclamations = 0;
    foreach ($reclamationsByType as $reclamationType) {
        if ($reclamationType['count'] > $maxReclamations) {
            $typeWithMostReclamations = $reclamationType['type'];
            $maxReclamations = $reclamationType['count'];
        }
    }
    
    
    return $this->render('reclamation/adminStats.html.twig', [
        'totalReclamations' => $totalReclamations,
        'totalResponses' => $totalResponses,
        'averageResponsesPerReclamation' => $averageResponsesPerReclamation,
        'resolvedReclamations' => $resolvedReclamations,
        'unresolvedReclamations' => $unresolvedReclamations,
        'percentageResolvedReclamations' => $percentageResolvedReclamations,
        'percentageUnresolvedReclamations' => $percentageUnresolvedReclamations,
        'reclamationsByType' => $reclamationsByType,
        'typeWithMostReclamations' => $typeWithMostReclamations,
    ]);
}

    
    






}


