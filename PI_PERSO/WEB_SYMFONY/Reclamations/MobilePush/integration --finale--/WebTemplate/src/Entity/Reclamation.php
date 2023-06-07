<?php

namespace App\Entity;

use App\Repository\ReclamationRepository;
use Doctrine\DBAL\Types\Types;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;

/**
 * Reclamation
 *
 * @ORM\Table(name="reclamation", indexes={@ORM\Index(name="fk_userid_rec", columns={"user_id"})})
 * @ORM\Entity(repositoryClass = "App\Repository\ReclamationRepository")
 */
class Reclamation
{
    /**
     * @var int
     *
     * @ORM\Column(name="rec_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $recId;

    /**
     * @var string
     *
     * @ORM\Column(name="titre_rec", type="string", length=255, nullable=false)
     * @Assert\NotBlank(message="Le titre de la réclamation ne doit pas être vide")
     *@Assert\Length(
    *      min = 2,
    *      minMessage = "La titre doit contenir au moins {{ limit }} caractères."
    * )
     */
    private $titreRec;

    /**
     * @var string
     *
     * @ORM\Column(name="type", type="string", length=255, nullable=false)
     */
    private $type;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="text", length=65535, nullable=false)
     * @Assert\NotBlank(message="Assurez-vous d'écrire une description pour votre réclamation")
     *@Assert\Length(
    *      min = 10,
    *      minMessage = "La Description doit contenir au moins {{ limit }} caractères."
    * )
     */
    private $description;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateCreation", type="date", nullable=false)
     */
    private $dateCreation;

    /**
     * @var \DateTime|null
     *
     * @ORM\Column(name="dateFin", type="date", nullable=true, options={"default"="NULL"})
     */
    private $dateFin = null;

    /**
     * @var string
     *
     * @ORM\Column(name="status", type="string", length=255, nullable=false, options={"default"="'Ouvert'"})
     */
    private $status = 'Ouvert';

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="user_id", referencedColumnName="id_user")
     * })
     */
    private $user;

    public function getRecId(): ?int
    {
        return $this->recId;
    }

    public function getTitreRec(): ?string
    {
        return $this->titreRec;
    }

    public function setTitreRec(string $titreRec): self
    {
        $this->titreRec = $titreRec;

        return $this;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): self
    {
        $this->type = $type;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getDateCreation(): ?\DateTimeInterface
    {
        return $this->dateCreation;
    }

    public function setDateCreation(\DateTimeInterface $dateCreation): self
    {
        $this->dateCreation = $dateCreation;

        return $this;
    }

    public function getDateFin(): ?\DateTimeInterface
    {
        return $this->dateFin;
    }

    public function setDateFin(?\DateTimeInterface $dateFin): self
    {
        $this->dateFin = $dateFin;

        return $this;
    }

    public function getStatus(): ?string
    {
        return $this->status;
    }

    public function setStatus(string $status): self
    {
        $this->status = $status;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): self
    {
        $this->user = $user;

        return $this;
    }


}
