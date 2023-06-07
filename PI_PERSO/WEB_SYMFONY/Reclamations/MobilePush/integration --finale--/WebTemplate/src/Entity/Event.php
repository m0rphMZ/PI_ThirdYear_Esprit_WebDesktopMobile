<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\EventRepository;

/**
 * Event
 *
 * @ORM\Table(name="event", indexes={@ORM\Index(name="host_fk", columns={"host_id"}), @ORM\Index(name="fk_locid", columns={"location_id"})})
 * @ORM\Entity(repositoryClass = "App\Repository\EventRepository")
 */
class Event
{
    /**
     * @var int
     *
     * @ORM\Column(name="event_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $eventId;

    /**
     * @var string
     *
     * @ORM\Column(name="title", type="string", length=255, nullable=false)
     */
    private $title;

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
     */
    private $description;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="startDate", type="date", nullable=false)
     */
    private $startdate;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="endDate", type="date", nullable=false)
     */
    private $enddate;

    /**
     * @var int
     *
     * @ORM\Column(name="ticketCount", type="integer", nullable=false)
     */
    private $ticketcount;

    /**
     * @var string
     *
     * @ORM\Column(name="affiche", type="string", length=255, nullable=false)
     */
    private $affiche;

    /**
     * @var float
     *
     * @ORM\Column(name="ticketPrice", type="float", precision=10, scale=0, nullable=false)
     */
    private $ticketprice;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="host_id", referencedColumnName="id_user")
     * })
     */
    private $host;

    /**
     * @var \Location
     *
     * @ORM\ManyToOne(targetEntity="Location")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="location_id", referencedColumnName="num_loc")
     * })
     */
    private $location;

    

    // /**
    //  * Constructor
    //  */
    // public function __construct()
    // {
    //     $this->user = new \Doctrine\Common\Collections\ArrayCollection();
    // }

    public function getEventId(): ?int
    {
        return $this->eventId;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): self
    {
        $this->title = $title;

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

    public function getStartdate(): ?\DateTimeInterface
    {
        return $this->startdate;
    }

    public function setStartdate(\DateTimeInterface $startdate): self
    {
        $this->startdate = $startdate;

        return $this;
    }

    public function getEnddate(): ?\DateTimeInterface
    {
        return $this->enddate;
    }

    public function setEnddate(\DateTimeInterface $enddate): self
    {
        $this->enddate = $enddate;

        return $this;
    }

    public function getTicketcount(): ?int
    {
        return $this->ticketcount;
    }

    public function setTicketcount(int $ticketcount): self
    {
        $this->ticketcount = $ticketcount;

        return $this;
    }

    public function getAffiche(): ?string
    {
        return $this->affiche;
    }

    public function setAffiche(string $affiche): self
    {
        $this->affiche = $affiche;

        return $this;
    }

    public function getTicketprice(): ?float
    {
        return $this->ticketprice;
    }

    public function setTicketprice(float $ticketprice): self
    {
        $this->ticketprice = $ticketprice;

        return $this;
    }

    public function getHost(): ?User
    {
        return $this->host;
    }

    public function setHost(?User $host): self
    {
        $this->host = $host;

        return $this;
    }

    public function getLocation(): ?Location
    {
        return $this->location;
    }

    public function setLocation(?Location $location): self
    {
        $this->location = $location;

        return $this;
    }

    // /**
    //  * @return Collection<int, User>
    //  */
    // public function getUser(): Collection
    // {
    //     return $this->user;
    // }

    // public function addUser(User $user): self
    // {
    //     if (!$this->user->contains($user)) {
    //         $this->user->add($user);
    //     }

    //     return $this;
    // }

    // public function removeUser(User $user): self
    // {
    //     $this->user->removeElement($user);

    //     return $this;
    // }

}
