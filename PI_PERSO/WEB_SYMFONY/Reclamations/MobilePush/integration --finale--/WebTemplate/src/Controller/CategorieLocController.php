<?php

namespace App\Controller;

use App\Entity\CategorieLoc;
use App\Form\CategorieLocType;
use App\Repository\CategorieLocRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;
#[Route('/categories/loc')]
class CategorieLocController extends AbstractController
{
    #[Route('/', name: 'app_categorie_loc_index', methods: ['GET'])]
    public function index(Request $request,CategorieLocRepository $categorieLocRepository, PaginatorInterface $paginator): Response
    {
        /*return $this->render('categorie_loc/index.html.twig', [
            'categorie_locs' => $categorieLocRepository->findAll(),
        ]);*/



        $categorie_locs=$categorieLocRepository->findAll();
        $categorie_locs = $paginator->paginate(
            $categorie_locs, /* query NOT result */
           $request->query->getInt('page', 1),
            3
        );
        return $this->render('categorie_loc/index.html.twig', [
            'categorie_locs' => $categorie_locs,
        ]);

    }

    #[Route('/new', name: 'app_categorie_loc_new', methods: ['GET', 'POST'])]
    public function new(Request $request, CategorieLocRepository $categorieLocRepository): Response
    {
        $categorieLoc = new CategorieLoc();
        
        $form = $this->createForm(CategorieLocType::class, $categorieLoc);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $categorieLocRepository->save($categorieLoc, true);

            return $this->redirectToRoute('app_categorie_loc_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('categorie_loc/new.html.twig', [
            'categorie_loc' => $categorieLoc,
            'form' => $form,
        ]);
    }

    #[Route('/{codecLoc}', name: 'app_categorie_loc_show', methods: ['GET'])]
    public function show(CategorieLoc $categorieLoc): Response
    {
        return $this->render('categorie_loc/show.html.twig', [
            'categorie_loc' => $categorieLoc,
        ]);
    }

    #[Route('/{codecLoc}/edit', name: 'app_categorie_loc_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, CategorieLoc $categorieLoc, CategorieLocRepository $categorieLocRepository): Response
    {
        $form = $this->createForm(CategorieLocType::class, $categorieLoc);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $categorieLocRepository->save($categorieLoc, true);

            return $this->redirectToRoute('app_categorie_loc_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('categorie_loc/edit.html.twig', [
            'categorie_loc' => $categorieLoc,
            'form' => $form,
        ]);
    }

    #[Route('/{codecLoc}', name: 'app_categorie_loc_delete', methods: ['POST'])]
    public function delete(Request $request, CategorieLoc $categorieLoc, CategorieLocRepository $categorieLocRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$categorieLoc->getCodecLoc(), $request->request->get('_token'))) {
            $categorieLocRepository->remove($categorieLoc, true);
        }

        return $this->redirectToRoute('app_categorie_loc_index', [], Response::HTTP_SEE_OTHER);
    }
}
/*




{% extends 'adminBase.html.twig' %}

{% block title %}CategorieLoc index{% endblock %}

{% block cards %}
    <h1>Liste des Categories</h1>
  


    <table class="table">
        <thead>
            <tr>
                <th>CodecLoc</th>
                <th>LibellecLoc</th>
                <th>actions</th>
            </tr>
        </thead>
        <tbody>
        {% for categorie_loc in categorie_locs %}
            <tr>
                <td>{{ categorie_loc.codecLoc }}</td>
                <td>{{ categorie_loc.libellecLoc }}</td>
                <td>
                    <a href="{{ path('app_categorie_loc_show', {'codecLoc': categorie_loc.codecLoc}) }}">show</a>
                    <a href="{{ path('app_categorie_loc_edit', {'codecLoc': categorie_loc.codecLoc}) }}">edit</a>
                </td>
            </tr>
        {% else %}
            <tr>
                <td colspan="3">no records found</td>
            </tr>
        {% endfor %}
        </tbody>
    </table>

    <a href="{{ path('app_categorie_loc_new') }}">Create new</a>
    
{% endblock %}
*/ 