package br.com.mercadolivre.mutantidentifier.analysis.helpers;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class HashHolderTest {

    @Test(expected = RuntimeException.class)
    public void getHashBeforeComputeTotal() {
        final String line = "ABCD";

        final HashHolder hashHolder = new HashHolder(2, 3);
        hashHolder.computeLine(line);
        hashHolder.getHash();
    }

    @Test
    public void getHashComputingTotalBeforeBufferSize() {
        final String line = "ABCD";
        final String expectedHash = DigestUtils.sha256Hex(line + line);

        final HashHolder hashHolder = new HashHolder(2, 3);
        hashHolder.computeLine(line);
        hashHolder.computeLine(line);

        final String hash = hashHolder.getHash();
        assertEquals(expectedHash, hash);
    }

    @Test
    public void getHashComputingTotalAfterBufferSize() {
        final String line = "ABCD";
        final String expectedHash = DigestUtils.sha256Hex( line + DigestUtils.sha256Hex(line));

        final HashHolder hashHolder = new HashHolder(2, 1);
        hashHolder.computeLine(line);
        hashHolder.computeLine(line);

        final String hash = hashHolder.getHash();
        assertEquals(expectedHash, hash);
    }

}
