<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;

class UserType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom')
            ->add('prenom')
            ->add('email')
            ->add('mdp', PasswordType::class)
            ->add('tel', NumberType::class, [
                'invalid_message' => 'Le numéro de téléphone doit être numérique.'])
                ->add('image', FileType::class, [
                    'label' => 'image',
                    'mapped' => false,
                    'required' => false,
                    'constraints' => [
                        new File([
                            'maxSize' => '2Mi',
                            'mimeTypes' => [
                                'image/jpeg',
                                'image/png',
                                'image/gif'
                            ],
                            'mimeTypesMessage' => 'Veuillez sélectionner une image ',
                        ]),
                        new NotBlank([
                            'message' => 'Veuillez sélectionner une image',
                        ]),
                    ],
                ])
           ->add('role', ChoiceType::class, [
    'choices' => [
        'Artiste' => 'Artiste',
        'Simple Utilisateur' => 'simple utilisateur',
        
    ],
    'expanded' => true,
    'multiple' => false,
    
])

           
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
