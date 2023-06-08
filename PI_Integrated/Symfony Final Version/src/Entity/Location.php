<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

use Symfony\Component\HttpFoundation\File\File;
use Vich\UploaderBundle\Mapping\Annotation as Vich;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Location
 *
 * @ORM\Table(name="location", indexes={@ORM\Index(name="fk_codeC", columns={"code_catg"}), @ORM\Index(name="fk_user", columns={"user_id"})})
 * @ORM\Entity(repositoryClass = "App\Repository\LocationRepository")
 * @Vich\Uploadable
 */
class Location
{
    /**
     * @var int
     *
     * @ORM\Column(name="num_loc", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $numLoc;

    /**
     * @var string
     *
     * @ORM\Column(name="descipt_loc", type="text", length=65535, nullable=false)
     */
    private $desciptLoc;

    /**
     * @var string
     *
     * @ORM\Column(name="lieu_loc", type="string", length=255, nullable=false)
     */
    private $lieuLoc;

    /**
     * @var float
     *
     * @ORM\Column(name="surface_loc", type="float", precision=10, scale=0, nullable=false)
     */
    private $surfaceLoc;

    /**
     * @var int
     *
     * @ORM\Column(name="nb_pers_loc", type="integer", nullable=false)
     */
    private $nbPersLoc;

    /**
     * @var float
     *
     * @ORM\Column(name="prix_loc", type="float", precision=10, scale=0, nullable=false)
     */
    private $prixLoc;

    /**
     * @var string|null
     *
     * @ORM\Column(name="equipements", type="string", length=1900, nullable=true)
     */
    private $equipements;

    /**
     * @var string
     *
     * @ORM\Column(name="disponibilite", type="string", length=255, nullable=false)
     */
    private $disponibilite;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_Aloc", type="date", nullable=false)
     */
    private $dateAloc;






    

/**
     * note
     *
     
     * @Vich\UploadableField(mapping="image_local", fileNameProperty="image")
     * @var File
     */
    private ?File $imageFile = null;


    
    /**
     * @var string
     *
     * @ORM\Column(name="image", type="string", length=255, nullable=false)
     */
    private $image;




    /**
     * @var \CategorieLoc
     *
     * @ORM\ManyToOne(targetEntity="CategorieLoc")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="code_catg", referencedColumnName="codeC_loc")
     * })
     */
    private $codeCatg;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="user_id", referencedColumnName="id_user")
     * })
     */
    private $user;

    public function getNumLoc(): ?int
    {
        return $this->numLoc;
    }

    public function getDesciptLoc(): ?string
    {
        return $this->desciptLoc;
    }

    public function setDesciptLoc(string $desciptLoc): self
    {
        $this->desciptLoc = $desciptLoc;

        return $this;
    }

    public function getLieuLoc(): ?string
    {
        return $this->lieuLoc;
    }

    public function setLieuLoc(string $lieuLoc): self
    {
        $this->lieuLoc = $lieuLoc;

        return $this;
    }

    public function getSurfaceLoc(): ?float
    {
        return $this->surfaceLoc;
    }

    public function setSurfaceLoc(float $surfaceLoc): self
    {
        $this->surfaceLoc = $surfaceLoc;

        return $this;
    }

    public function getNbPersLoc(): ?int
    {
        return $this->nbPersLoc;
    }

    public function setNbPersLoc(int $nbPersLoc): self
    {
        $this->nbPersLoc = $nbPersLoc;

        return $this;
    }

    public function getPrixLoc(): ?float
    {
        return $this->prixLoc;
    }

    public function setPrixLoc(float $prixLoc): self
    {
        $this->prixLoc = $prixLoc;

        return $this;
    }

    public function getEquipements(): ?string
    {
        return $this->equipements;
    }

    public function setEquipements(?string $equipements): self
    {
        $this->equipements = $equipements;

        return $this;
    }

    public function getDisponibilite(): ?string
    {
        return $this->disponibilite;
    }

    public function setDisponibilite(string $disponibilite): self
    {
        $this->disponibilite = $disponibilite;

        return $this;
    }

    public function getDateAloc(): ?\DateTimeInterface
    {
        return $this->dateAloc;
    }

    public function setDateAloc(\DateTimeInterface $dateAloc): self
    {
        $this->dateAloc = $dateAloc;

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

    public function getCodeCatg(): ?CategorieLoc
    {
        return $this->codeCatg;
    }

    public function setCodeCatg(?CategorieLoc $codeCatg): self
    {
        $this->codeCatg = $codeCatg;

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


    /**
      * @param File|\Symfony\Component\HttpFoundation\File\UploadedFile $imageFile
     */
    public function setImageFile(?File $image = null): void
    {
        $this->imageFile = $image;

        if (null !== $image) {
            // It is required that at least one field changes if you are using doctrine
            // otherwise the event listeners won't be called and the file is lost
            $this->dateAloc = new \DateTimeImmutable();
        }
    }

   

    public function getImageFile(): ?File
    {
        return $this->imageFile;
    }
}
