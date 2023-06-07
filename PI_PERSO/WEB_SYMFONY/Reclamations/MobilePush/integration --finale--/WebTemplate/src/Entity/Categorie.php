<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Categorie
 *
 * @ORM\Table(name="categorie")
 * @ORM\Entity
 */
class Categorie
{
    /**
     * @var int
     *
     * @ORM\Column(name="idCategorie", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idcategorie;

    /**
     * @var string
     *
     * @ORM\Column(name="libcategorie", type="string", length=255, nullable=false)
     */
    private $libcategorie;

  

    public function getIdcategorie(): ?int
    {
        return $this->idcategorie;
    }

    public function getLibcategorie(): ?string
    {
        return $this->libcategorie;
    }

    public function setLibcategorie(string $libcategorie): self
    {
        $this->libcategorie = $libcategorie;

        return $this;
    }
    
    public function __toString(): string
    {
        return $this->getLibcategorie();
    }


}
