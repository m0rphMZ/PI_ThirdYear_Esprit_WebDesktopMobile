<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Redection
 *
 * @ORM\Table(name="redection")
 * @ORM\Entity
 */
class Redection
{
    /**
     * @var string
     *
     * @ORM\Column(name="coder", type="string", length=255, nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $coder;

    /**
     * @var float
     *
     * @ORM\Column(name="valr", type="float", precision=10, scale=0, nullable=false)
     */
    private $valr;

    public function getCoder(): ?string
    {
        return $this->coder;
    }

    public function getValr(): ?float
    {
        return $this->valr;
    }

    public function setValr(float $valr): self
    {
        $this->valr = $valr;

        return $this;
    }


}
