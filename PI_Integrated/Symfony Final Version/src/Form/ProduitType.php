<?php

namespace App\Form;

use App\Entity\Produit;
use App\Entity\Categorie;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Validator\Constraints\Range;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Constraints\Image;





class ProduitType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('codeproduit',TextType::class,[
                'label' => 'Code Produit',
                'attr' => ['placeholder' => 'Code du produit',
                'class' => 'form-control'],
                'constraints' => [
                    new NotBlank([
                        'message' => 'Le champ nom est obligatoire'
                    ]),
                    new Assert\Length([
                        'max' => 12,
                        'maxMessage' => 'Le nom ne doit pas dépasser  12 caractères.'
                    ]),
                    new Assert\Length([
                        'min' => 4,
                        'maxMessage' => 'Le nom ne doit pas etre inferieur à 4  caractères.'
                    ])
                ]
                
                ])
                
            ->add('des',TextareaType::class,[
                'label' => 'Description',
                'attr' => ['placeholder' => 'Description du produit',
                'class' => 'form-control'
                ]
                ])
            ->add('qtestock',IntegerType::class,[
                'label' => 'Quantité stock',
                'required' => true,
                //'data' => 1,
                'attr' => [
                    'class' => 'form-control',
                    
                ],
                'constraints' => [
                    new Range([
                        'min' => 1,
                        'minMessage' => 'La valeur doit être supérieure ou égale à 1',
                       
                    ]),
                ],
                ])
            ->add('qtemin',IntegerType::class,[
                'label' => 'Quantité min',
                'required' => true,
                //'data' => 1,
                'attr' => [
                    'class' => 'form-control',
                    
                ],
                'constraints' => [
                    new Range([
                        'min' => 1,
                        'minMessage' => 'La valeur doit être supérieure ou égale à 1',
                       
                    ]),
                ],
                ])
            ->add('prixunitaire',IntegerType::class,[
                'label' => 'Prix',
                'required' => true,
                //'data' => 1,
                'attr' => [
                    'class' => 'form-control',
                    
                ],
                'constraints' => [
                    new Range([
                        'min' => 1,
                        'minMessage' => 'La valeur doit être supérieure ou égale à 1',
                       
                    ]),
                ],
                ])
            ->add('idunite',TextType::class,[
                'label' => 'id unite',
                'attr' => ['placeholder' => 'Description du produit',
                'class' => 'form-control'
                ]
                ])
                ->add('imageFile', FileType::class, [
                    'label' => 'Photo',
                    'required' => false,
                    'constraints' => [
                        new NotBlank([
                            'message' => 'Veuillez télécharger une image'
                        ]),
                        new Image([
                            'maxSize' => '2M',
                            'maxSizeMessage' => 'La taille de l\'image ne doit pas dépasser 2 Mo',
                            'mimeTypes' => [
                                'image/jpeg',
                                'image/png'
                            ],
                            'mimeTypesMessage' => 'Le format de l\'image doit être JPEG ou PNG'
                        ])
                    ]
                ])

                ->add('idcat',TextType::class,[
                    'label' => 'Categorie',
                    'attr' => ['placeholder' => 'Categorie',
                    'class' => 'form-control'
                    ]
                    ])
                // ->add('idcat', EntityType::class, [
                //         'class' => Categorie::class,
                //         'choice_label' => 'libcategorie',
                //     ])
              
                ->add('Enregistrer', SubmitType::class, [
                    'attr' => [
                        'class' => 'form-control',
                        
                    ],
                ])
        ;
    }


    
    
       

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Produit::class,
        ]);
    }
}
