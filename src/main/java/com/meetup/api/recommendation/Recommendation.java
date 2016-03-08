package com.meetup.api.recommendation;

public class Recommendation
{
    private Integer id;

    private Integer locationId;

    private String lead;

    private String alternateLead;

    public Recommendation()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public Recommendation(Integer id, Integer locationId, String lead, String alternateLead)
    {
        super();
        this.id = id;
        this.locationId = locationId;
        this.lead = lead;
        this.alternateLead = alternateLead;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getLocationId()
    {
        return locationId;
    }

    public void setLocationId(Integer locationId)
    {
        this.locationId = locationId;
    }

    public String getLead()
    {
        return lead;
    }

    public void setLead(String lead)
    {
        this.lead = lead;
    }

    public String getAlternateLead()
    {
        return alternateLead;
    }

    public void setAlternateLead(String alternateLead)
    {
        this.alternateLead = alternateLead;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alternateLead == null) ? 0 : alternateLead.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lead == null) ? 0 : lead.hashCode());
        result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Recommendation other = (Recommendation) obj;
        if (alternateLead == null)
        {
            if (other.alternateLead != null)
                return false;
        }
        else if (!alternateLead.equals(other.alternateLead))
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (lead == null)
        {
            if (other.lead != null)
                return false;
        }
        else if (!lead.equals(other.lead))
            return false;
        if (locationId == null)
        {
            if (other.locationId != null)
                return false;
        }
        else if (!locationId.equals(other.locationId))
            return false;
        return true;
    }

}
