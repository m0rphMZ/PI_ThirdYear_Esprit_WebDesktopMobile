<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\UserRepository;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;

/**
 * User
 *
 * @ORM\Table(name="user")
 *@ORM\Entity(repositoryClass = "App\Repository\UserRepository") 
 */

 
 
class User 
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_user", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idUser;

    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="string", length=255, nullable=false)
     * @Assert\NotBlank(message="remplir le nom")
     */
    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="prenom", type="string", length=255, nullable=false)
     * @Assert\NotBlank(message="remplir le prenom")
     */
    private $prenom;

    /**
     * @var string
     *
     * @ORM\Column(name="email", type="string", length=255, nullable=false)
     * @Assert\NotBlank(message="remplir votre Email")
     * @Assert\Email(
     *     message = "Email non valide")
     */
    private $email;

    /**
     * @var string
     *
     * @ORM\Column(name="mdp", type="string", length=255, nullable=true)
     * @Assert\NotBlank(message="remplir votre mot de passe")
   
     */
    private $mdp;

    /**
     * @var int
     *
     * @ORM\Column(name="tel", type="integer", nullable=false)
     * @Assert\NotBlank(message="remplir votre téléphone")
     * @Assert\Type(
     *     type="integer",
     *     message="il faut entrer une valeur numérique")
     */
    private $tel;

    /**
     * @var string
     *
     * @ORM\Column(name="image", type="text", length=0, nullable=false)
  
     */
    private $image;

    /**
     * @var string
     *
     * @ORM\Column(name="role", type="string", length=50, nullable=false)
      * @Assert\NotBlank(message="selectionner un role ")
     */
    private $role;

    
     /**
     * @var string|null
     *
     * @ORM\Column(name="etat", type="string", length=50, nullable=true, options={"default"="activé"})
     */
    private $etat = 'activé';
    

    public function getIdUser(): ?int
    {
        return $this->idUser;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(string $prenom): self
    {
        $this->prenom = $prenom;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getMdp(): ?string
    {
        return $this->mdp;
    }

    public function setMdp(string $mdp): self
    {
        $this->mdp = $mdp;

        return $this;
    }

    public function getTel(): ?int
    {
        return $this->tel;
    }

    public function setTel(int $tel): self
    {
        $this->tel = $tel;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }

    public function getRole(): ?string
    {
        return $this->role;
    }

    public function setRole(string $role): self
    {
        $this->role = $role;

        return $this;
    }

    public function getEtat(): ?string
    {
        return $this->etat;
    }

    public function setEtat(?string $etat): self
    {
        $this->etat = $etat;

        return $this;
    }

 
 
 

}
