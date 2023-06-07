<?php

namespace App\Controller;

use App\Repository\LocationRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

class ArbiaUpdateController extends AbstractController
{
    #[Route('/LocalShow/{id}', name: 'Local_Show')]
    public function getRecParRecId($id, Request $request, NormalizerInterface $Normalizer, LocationRepository $reclamationRepository)
    {
        $id = $request->get('id');
        $reclamation = $reclamationRepository->find($id);
        $reclamationNormalises = $Normalizer->normalize($reclamation, 'json');

        $json = json_encode($reclamationNormalises);

        return new Response($json);
    }

}
