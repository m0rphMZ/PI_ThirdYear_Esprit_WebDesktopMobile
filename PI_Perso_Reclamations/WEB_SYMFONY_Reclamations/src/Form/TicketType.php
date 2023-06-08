<?php

namespace App\Form;

use App\Entity\Ticket;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Form\FormInterface;
use Symfony\Component\Form\FormError;
use Symfony\Component\Validator\Constraints\Callback;
use Symfony\Component\Validator\Context\ExecutionContextInterface;

//User & Event:
use App\Entity\User;
use App\Entity\Event;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;

class TicketType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            // ->add('price')
            // ->add('qrcodeimg')
            ->add('user', EntityType::class, [
                'class' => User::class,
                'choice_label' => 'prenom',
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez choisir un utilisateur',
                    ]),
                ],
            ])
            ->add('event', EntityType::class, [
                'class' => Event::class,
                'choice_label' => 'title',
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez choisir un évenement',
                    ]),
                    new Callback([$this, 'validateEventTicketCount']),
                ],
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Ticket::class,
            'user' => null,
            'event' => null,
        ]);
    }

    public function validateEventTicketCount($value, ExecutionContextInterface $context)
{
    $form = $context->getObject();
    $event = $value;
    
    // Check if the event has any tickets left
    if ($event && $event->getTicketcount() === 0) {
        $form->addError(new FormError('Il n\'y a plus de tickets disponibles pour cet événement.'));
    }
}
}
