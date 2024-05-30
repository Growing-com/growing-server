package org.sarangchurch.growing.core.types;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OffsetBasedPageRequest implements Pageable, Serializable {

    private static final long serialVersionUID = -25822477129613575L;

    private final int offset;
    private final int limit;

    public OffsetBasedPageRequest(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public Slice<?> of(List<?> content) {
        boolean hasNext = false;
        if (content.size() > getPageSize()) {
            content.remove(getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, this, hasNext);
    }

    @Override
    public boolean isUnpaged() {
        return Pageable.super.isUnpaged();
    }

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return Sort.unsorted();
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return Pageable.super.getSortOr(sort);
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest((int) (getOffset() + getPageSize()), limit);
    }

    public OffsetBasedPageRequest previous() {
        return hasPrevious()
                ? new OffsetBasedPageRequest((int) (getOffset() - getPageSize()), limit)
                : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(0, limit);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Pageable.super.toOptional();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffsetBasedPageRequest that = (OffsetBasedPageRequest) o;
        return getOffset() == that.getOffset();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOffset());
    }

    @Override
    public String toString() {
        return "OffsetBasedPageRequest{" +
                "offset=" + offset +
                '}';
    }
}
