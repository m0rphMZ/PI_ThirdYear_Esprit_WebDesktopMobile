<?php

namespace App\Form;

use App\Entity\Event;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\LessThanOrEqual;
use Symfony\Component\Validator\Constraints\GreaterThanOrEqual;

//Location:
use App\Entity\Location;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;

class EventType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('title', TextType::class, [
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez remplir ce champ',
                    ]),
                ],
            ])
            ->add('type', TextType::class, [
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez remplir ce champ',
                    ]),
                ],
            ])
            ->add('description', TextType::class, [
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez remplir ce champ',
                    ]),
                ],
            ])
            ->add('startdate', DateType::class, [
                'widget' => 'single_text',
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez remplir ce champ',
                    ]),
                    new LessThanOrEqual([
                        'propertyPath' => 'parent.all[enddate].data',
                        'message' => 'La date de début doit être avant ou égale à la date de fin.',
                    ]),
                ],
            ])
            ->add('enddate', DateType::class, [
                'widget' => 'single_text',
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez remplir ce champ',
                    ]),
                    // new GreaterThanOrEqual([
                    //     'value' => 'today',
                    //     'message' => 'La date de fin doit être avant ou égale à la date d\'aujourd\'hui.',
                    // ]),
                    new GreaterThanOrEqual([
                        'propertyPath' => 'parent.all[startdate].data',
                        'message' => 'La date de début doit être apres ou égale à la date de fin.',
                    ]),
                ],
            ])
            ->add('ticketcount', NumberType::class, [
                'invalid_message' => 'Le nombre des tickets doit être numérique.'])
            ->add('affiche', FileType::class, [
                'label' => 'Affiche',
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
                        'mimeTypesMessage' => 'Veuillez choisir une image',
                    ]),
                    new NotBlank([
                        'message' => 'Veuillez choisir une image',
                    ]),
                ],
            ])
            ->add('ticketprice', NumberType::class, [
                'invalid_message' => 'Le prix du ticket doit être numérique.'])
            //->add('host')
            ->add('location', EntityType::class, [
                'class' => Location::class,
                'choice_label' => 'lieu_loc',
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez choisir une localisation',
                    ]),
                ],
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Event::class,
            'location' => null,
        ]);
    }
}
