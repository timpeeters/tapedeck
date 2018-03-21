package io.tapedeck;

public class HeaderTest implements EqualsContractTester<Header>, HashCodeContractTester<Header> {
    @Override
    public Header getInstance() {
        return Header.builder().withName("Accept").withValue("text/html").build();
    }
}
