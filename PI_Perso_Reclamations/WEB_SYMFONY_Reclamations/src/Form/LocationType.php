<?php

namespace App\Form;

use App\Entity\Location;
use App\Entity\User;
use App\Entity\CategorieLoc;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Vich\UploaderBundle\Form\Type\VichImageType;

class LocationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('desciptLoc',TextType::class, [
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez remplir ce champ',
                    ]),
                ],
            ])
            ->add('lieuLoc',TextType::class, [
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez remplir ce champ',
                    ]),
                ],
            ])
            ->add('surfaceLoc',NumberType::class,[
                'invalid_message' => 'Le surface de téléphone doit être numérique.'])
            ->add('nbPersLoc',NumberType::class,[
                'invalid_message' => 'Le nbre des pers de téléphone doit être numérique.'])
            ->add('prixLoc',NumberType::class,[
                'invalid_message' => 'Le prix de téléphone doit être numérique.'])
            ->add('equipements',TextType::class, [
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez remplir ce champ',
                    ]),
                ],
            ])
            ->add('disponibilite',ChoiceType::class, [
                'choices' => [
                    'disponible' => 'oui',
                    'non disponible' => 'non',
                  
                ],
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez remplir ce champ',
                    ]),
                ],
            ])
            ->add('dateAloc',DateType::class, [
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez remplir ce champ',
                    ]),
                ],
            ])
           /* ->add('image', FileType::class, [
                'label' => 'image (JPG, PNG, GIF)',
                'required' => true,
                'mapped' => false,
                'constraints' => [
                    new File([
                        'maxSize' => '2M',
                        'mimeTypes' => [
                            'image/jpeg',
                            'image/png',
                            'image/gif',
                        ],
                        'mimeTypesMessage' => 'Please upload a valid image (JPG, PNG, GIF)',
                    ]),
                    new NotBlank([
                        'message' => 'Veuillez choisir une image',
                    ]),
                ],
            ])
          */
            ->add('imageFile',VichImageType::class)
          
            ->add('codeCatg',EntityType::class,[
            'class'=>CategorieLoc::class,
           'choice_label'=>'codeC_Loc',
           
          
          'constraints' => [
            new NotBlank([
                'message' => 'Veuillez choisir une categorie',
            ]),
        ],
    ])
          
         // ->add('user')
         ->add('user',EntityType::class,[
            'class'=>User::class,
           'choice_label'=>'id_user',
           'multiple'=>false,     //par defaut false si j'ajoute pas multiple et expanded
          'expanded'=>false])
   
        ;



    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Location::class,
        ]);
    }
}
