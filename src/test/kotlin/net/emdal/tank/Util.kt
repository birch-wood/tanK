package net.emdal.tank

import org.assertj.core.api.Assertions.assertThat


infix fun Any?.isEqualTo(other:Any?) {
  assertThat(this).isEqualTo(other)
}