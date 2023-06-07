<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * LikeComment
 *
 * @ORM\Table(name="like_comment", indexes={@ORM\Index(name="fk_iduser", columns={"id_user"}), @ORM\Index(name="fk_id_com", columns={"id_com"})})
 * @ORM\Entity
 */
class LikeComment
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_like", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idLike;

    /**
     * @var bool
     *
     * @ORM\Column(name="etat", type="boolean", nullable=false)
     */
    private $etat;

    /**
     * @var \Commentaire
     *
     * @ORM\ManyToOne(targetEntity="Commentaire")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_com", referencedColumnName="id_com")
     * })
     */
    private $idCom;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="id_user")
     * })
     */
    private $idUser;

    public function getIdLike(): ?int
    {
        return $this->idLike;
    }

    public function isEtat(): ?bool
    {
        return $this->etat;
    }

    public function setEtat(bool $etat): self
    {
        $this->etat = $etat;

        return $this;
    }

    public function getIdCom(): ?Commentaire
    {
        return $this->idCom;
    }

    public function setIdCom(?Commentaire $idCom): self
    {
        $this->idCom = $idCom;

        return $this;
    }

    public function getIdUser(): ?User
    {
        return $this->idUser;
    }

    public function setIdUser(?User $idUser): self
    {
        $this->idUser = $idUser;

        return $this;
    }


}
