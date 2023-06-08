<?php

namespace App\Form;

use App\Entity\CategorieLoc;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
class CategorieLocType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            
            ->add('libellecLoc',TextType::class,[
                'invalid_message' => 'Le libelle de téléphone doit être numérique.'])
                ->add('color',TextType::class,[
                    'invalid_message' => 'Le coleur doit être hexa.'])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => CategorieLoc::class,
        ]);
    }
}
