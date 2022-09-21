package com.ingenuity.transform;

import java.util.*;

public class TransformLinkSet<E> extends AbstractSet<TransformLink> {

    private Set<TransformLink> tLinkSet = new HashSet<TransformLink>();

    @Override
    public boolean add(TransformLink tl){

        if (!tLinkSet.contains(tl)){
            tLinkSet.add(tl);
            return true;
        } else
            return false;

    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Set))
            return false;
        Collection<?> c = (Collection<?>) o;
        if (c.size() != size())
            return false;
        try {
            return containsAll(c);
        } catch (ClassCastException unused)   {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int h = 0;
        Iterator<TransformLink> i = iterator();
        while (i.hasNext()) {
            TransformLink obj = i.next();
            if (obj != null)
                h += obj.getTransformLinkName().hashCode();
        }
        return h;
    }

    @Override
    public Iterator<TransformLink> iterator() {
        return tLinkSet.iterator();
    }

    @Override
    public int size() {
        return tLinkSet.size();
    }

    @Override
    public boolean contains(Object in) {

        if (! (in instanceof TransformLink)){
            return false;
        }

        TransformLink o =   (TransformLink) in;
        Iterator<TransformLink> it = iterator();
        if (o==null) {
            while (it.hasNext())
                if (it.next()==null)
                    return true;
        } else {
            while (it.hasNext())
                if (o.getTransformLinkName().equals(it.next().getTransformLinkName()))
                    return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {

        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }
}
