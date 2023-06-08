<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * CategorieLoc
 *
 * @ORM\Table(name="categorie_loc")
 * @ORM\Entity(repositoryClass = "App\Repository\CategorieLocRepository")
 */
class CategorieLoc
{
    /**
     * @var int
     *
     * @ORM\Column(name="codeC_loc", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $codecLoc;

    /**
     * @var string
     *
     * @ORM\Column(name="libelleC_loc", type="string", length=255, nullable=true)
     */
    private $libellecLoc;

    /**
     * @var string
     *
     * @ORM\Column(name="color", type="string", length=255, nullable=true)
     */
    private $color;

    public function getCodecLoc(): ?int
    {
        return $this->codecLoc;
    }

    public function getLibellecLoc(): ?string
    {
        return $this->libellecLoc;
    }

    public function setLibellecLoc(string $libellecLoc): self
    {
        $this->libellecLoc = $libellecLoc;

        return $this;
    }
    public function getColor(): ?string
    {
        return $this->color;
    }

    public function setColor(string $color): self
    {
        $this->color = $color;

        return $this;
    }

}
