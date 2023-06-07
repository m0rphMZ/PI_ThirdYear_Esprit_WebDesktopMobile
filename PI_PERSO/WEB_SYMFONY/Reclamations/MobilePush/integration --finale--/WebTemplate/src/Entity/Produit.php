<?php

namespace App\Entity;
use Symfony\Component\HttpFoundation\File\File;
use Vich\UploaderBundle\Mapping\Annotation as Vich;

use Doctrine\ORM\Mapping as ORM;

/**
 * Produit
 *
 * @ORM\Table(name="produit", indexes={@ORM\Index(name="test", columns={"idcat"})})
 * @ORM\Entity
 */
class Produit
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="CodeProduit", type="string", length=255, nullable=false)
     */
    private $codeproduit;

    /**
     * @var string
     *
     * @ORM\Column(name="Des", type="string", length=255, nullable=false)
     */
    private $des;

    /**
     * @var int
     *
     * @ORM\Column(name="QteStock", type="integer", nullable=false)
     */
    private $qtestock;

    /**
     * @var int
     *
     * @ORM\Column(name="QteMin", type="integer", nullable=false)
     */
    private $qtemin;

    /**
     * @var int
     *
     * @ORM\Column(name="PrixUnitaire", type="integer", nullable=false)
     */
    private $prixunitaire;

    /**
     * @var string
     *
     * @ORM\Column(name="idUnite", type="string", length=255, nullable=false)
     */
    private $idunite;

    /**
     * @var string
     *
     * @ORM\Column(name="image", type="string", length=255, nullable=false)
     */
    private $image;

    /**
     * @var string
     *
     * @ORM\Column(name="idcat", type="string", length=255, nullable=false)
     */
    private $idcat;

    /**
     * @Vich\UploadableField(mapping="image", fileNameProperty="imageName")
     * 
     * @var File
     */
    private $imageFile;  
    


    public function setImageFile(?File $imageFile = null): void
    {
        $this->imageFile = $imageFile;
    }
    public function getImageFile(): ?File
    {
        return $this->imageFile;
    }
    public function getId(): ?int
    {
        return $this->id;
    }

    public function getCodeproduit(): ?string
    {
        return $this->codeproduit;
    }

    public function setCodeproduit(string $codeproduit): self
    {
        $this->codeproduit = $codeproduit;

        return $this;
    }

    public function getDes(): ?string
    {
        return $this->des;
    }

    public function setDes(string $des): self
    {
        $this->des = $des;

        return $this;
    }

    public function getQtestock(): ?int
    {
        return $this->qtestock;
    }

    public function setQtestock(int $qtestock): self
    {
        $this->qtestock = $qtestock;

        return $this;
    }

    public function getQtemin(): ?int
    {
        return $this->qtemin;
    }

    public function setQtemin(int $qtemin): self
    {
        $this->qtemin = $qtemin;

        return $this;
    }

    public function getPrixunitaire(): ?int
    {
        return $this->prixunitaire;
    }

    public function setPrixunitaire(int $prixunitaire): self
    {
        $this->prixunitaire = $prixunitaire;

        return $this;
    }

    public function getIdunite(): ?string
    {
        return $this->idunite;
    }

    public function setIdunite(string $idunite): self
    {
        $this->idunite = $idunite;

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

    public function getIdcat(): ?string
    {
        return $this->idcat;
    }

    public function setIdcat(string $idcat): self
    {
        $this->idcat = $idcat;

        return $this;
    }



}
